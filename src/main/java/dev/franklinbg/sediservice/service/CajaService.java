package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.*;
import dev.franklinbg.sediservice.entity.dto.CajaWithDetallesDTO;
import dev.franklinbg.sediservice.repository.*;
import dev.franklinbg.sediservice.utils.GenericResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import static dev.franklinbg.sediservice.utils.Global.*;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class CajaService {
    private final CajaRepository repository;
    private final DetalleCajaRepository detalleCajaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final MovCajaRepository movCajaRepository;
    private final ConceptoMovCajaRepository conceptoMovCajaRepository;
    private final AperturaRepository aperturaRepository;

    public CajaService(CajaRepository repository, DetalleCajaRepository detalleCajaRepository, MetodoPagoRepository metodoPagoRepository, MovCajaRepository movCajaRepository, ConceptoMovCajaRepository conceptoMovCajaRepository, AperturaRepository aperturaRepository) {
        this.repository = repository;
        this.detalleCajaRepository = detalleCajaRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.movCajaRepository = movCajaRepository;
        this.conceptoMovCajaRepository = conceptoMovCajaRepository;
        this.aperturaRepository = aperturaRepository;
    }

    public GenericResponse<Caja> findByUsuarioId(int idU) {
        Optional<Caja> optCaja = repository.findByUsuarioId(idU);
        return optCaja.map(caja -> new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, caja)).orElseGet(() -> new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se ha encontrado una caja asociada a este usuario"));
    }

    public GenericResponse<CajaWithDetallesDTO> open(CajaWithDetallesDTO dto) {
        Optional<Caja> optCaja = repository.findById(dto.getIdCaja());
        if (optCaja.isPresent()) {
            double montoApertura = 0.0;
            for (DetalleCaja detalle : dto.getDetalles()) {
                detalle.setFechaCreacion(new Date(new java.util.Date().getTime()));
                detalle.setMontoCierre(detalle.getMonto());
                montoApertura += detalle.getMonto();
            }
            detalleCajaRepository.saveAll(dto.getDetalles());
            Caja caja = optCaja.get();
            caja.setMontoApertura(montoApertura);
            caja.setMontoCierre(montoApertura);
            caja.setEstado('A');
            caja.setFechaApertura(new java.util.Date());
            Apertura apertura = new Apertura(caja, new Date(new java.util.Date().getTime()));
            aperturaRepository.save(apertura);
            repository.save(caja);
            return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "caja abierta correctamente", dto);
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontro la caja");
        }
    }

    public GenericResponse<Iterable<DetalleCaja>> close(int idCaja) {
        Optional<Caja> optionalCaja = repository.findById(idCaja);
        if (optionalCaja.isPresent()) {
            Caja caja = optionalCaja.get();
            if (caja.getEstado() == 'A') {
                caja.setEstado('C');
                caja.setFechaCierre(new java.util.Date());
                Iterable<DetalleCaja> detallesCaja = detalleCajaRepository.findAllByCajaIdAndCerradoIsFalse(caja.getId());
                for (DetalleCaja detalle : detallesCaja) {
                    detalle.setCerrado(true);
                }
                repository.save(caja);
                detalleCajaRepository.saveAll(detallesCaja);
                return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "caja cerrada correctamente", detallesCaja);
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "esta caja ya esta cerrada");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontro la caja");
        }
    }

    public GenericResponse<MovCaja> saveMovimiento(MovCaja movCaja) {
        movCaja.setEstado('P');
        if (movCaja.getTipoMov() == 'E' || movCaja.getTipoMov() == 'S') {
            Optional<Caja> optionalCaja = repository.findById(movCaja.getCaja().getId());
            if (optionalCaja.isPresent()) {
                Caja caja = optionalCaja.get();
                if (caja.getEstado() == 'A') {
                    movCaja.setCaja(caja);
                    Optional<MetodoPago> optionalMetodoPago = metodoPagoRepository.findById(movCaja.getMetodoPago().getId());
                    if (optionalMetodoPago.isPresent()) {
                        MetodoPago metodoPago = optionalMetodoPago.get();
                        Optional<ConceptoMovCaja> optionalConceptoMovCaja = conceptoMovCajaRepository.findById(movCaja.getConceptoMovCaja().getId());
                        if (optionalConceptoMovCaja.isPresent()) {
                            Optional<DetalleCaja> optionalDetalleCaja = detalleCajaRepository.findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(caja.getId(), metodoPago.getId());
                            if (optionalDetalleCaja.isPresent()) {
                                DetalleCaja detalleCaja = optionalDetalleCaja.get();
                                if (movCaja.getTipoMov() == 'E') {
                                    detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() + movCaja.getTotal());
                                    caja.setMontoCierre(caja.getMontoCierre() + movCaja.getTotal());
                                } else {
                                    if (movCaja.getTotal() <= detalleCaja.getMontoCierre()) {
                                        detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() - movCaja.getTotal());
                                        caja.setMontoCierre(caja.getMontoCierre() - movCaja.getTotal());
                                    } else {
                                        return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "salida rechazada,el monto excede el monto actualmente disponible en este metodo de pago,disponible:" + detalleCaja.getMontoCierre());
                                    }
                                }
                                repository.save(caja);
                                detalleCajaRepository.save(detalleCaja);
                                movCaja.setApertura(aperturaRepository.getLastRegister());
                                return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "movimiento registrado correctamente", movCajaRepository.save(movCaja));
                            } else {
                                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "detalle de caja no encontrado");
                            }
                        } else {
                            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "concepto de movimiento no encontrado");
                        }
                    } else {
                        return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "metodo de pago no encontrado");
                    }
                } else {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "esta caja esta cerrada");
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "caja no encontrada");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "tipo de movimiento no válido");
        }
    }

    public GenericResponse<MovCaja> anularMovimiento(int id) {
        Optional<MovCaja> optionalMovCaja = movCajaRepository.findById(id);
        if (optionalMovCaja.isPresent()) {
            MovCaja movCaja = optionalMovCaja.get();
            if (movCaja.getEstado() == 'A') {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "el movimiento que desea anular ya está anulado");
            }
            movCaja.setEstado('A');
            Caja caja = movCaja.getCaja();
            Optional<DetalleCaja> optionalDetalleCaja = detalleCajaRepository.findByCajaIdAndMetodoPagoIdAndFechaCreacion(movCaja.getCaja().getId(), movCaja.getMetodoPago().getId(), movCaja.getApertura().getFechaApertura());
            if (optionalDetalleCaja.isPresent()) {
                DetalleCaja detalleCaja = optionalDetalleCaja.get();
                if (!detalleCaja.isCerrado()) {
                    if (movCaja.getTipoMov() == 'E') {
                        detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() - movCaja.getTotal());
                        caja.setMontoCierre(caja.getMontoCierre() - movCaja.getTotal());
                    } else {
                        detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() + movCaja.getTotal());
                        caja.setMontoCierre(caja.getMontoCierre() + movCaja.getTotal());
                    }
                    repository.save(caja);
                    return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "movimiento anulado correctamente", movCajaRepository.save(movCaja));
                } else {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "el movimiento que deseas anular pertenece a un detalle de caja ya cerrado,operación improcedente");
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontró el detalle de caja a actualizar");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontró el movimiento a anular");
        }
    }

    public ResponseEntity<?> descargarReporte(int idCaja, String fechaApertura) {
        try {
            File xmlReport = ResourceUtils.getFile("classpath:reports/reporteMovCaja.jasper");
            Optional<Caja> optCaja = repository.findById(idCaja);
            if (optCaja.isPresent()) {
                Caja caja = optCaja.get();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateFechaApertura = new Date(simpleDateFormat.parse(fechaApertura).getTime());
                if (aperturaRepository.existsByCajaIdAndFechaApertura(caja.getId(), dateFechaApertura)) {
                    AtomicReference<Double> ingreso = new AtomicReference<>(0.0);
                    AtomicReference<Double> egreso = new AtomicReference<>(0.0);
                    Iterable<DetalleCaja> detalles = detalleCajaRepository.findAllByCajaIdAndFechaCreacion(idCaja, dateFechaApertura);
                    Iterable<MovCaja> movimientos = movCajaRepository.findAllByCajaIdAndAperturaFechaApertura(caja.getId(), dateFechaApertura);
                    movimientos.forEach(mov -> {
                        if (mov.getTipoMov() == 'E') {
                            ingreso.updateAndGet(v -> v + mov.getTotal());
                        } else {
                            egreso.updateAndGet(v -> v + mov.getTotal());
                        }
                    });
                    JasperReport compiledReport = (JasperReport) JRLoader.loadObject(xmlReport);
                    HashMap<String, Object> parameters = new HashMap<>();
                    parameters.put("vendedor", caja.getUsuario().getNombre());
                    parameters.put("estadoCaja", caja.getEstado() == 'A' ? "Abierta" : "Cerrada");
                    parameters.put("saldoInicial", caja.getMontoApertura());
                    parameters.put("saldoFinal", caja.getMontoCierre());
                    parameters.put("ingreso", ingreso.get());
                    parameters.put("egreso", egreso.get());
                    parameters.put("detallesDS", new JRBeanCollectionDataSource((Collection<?>) detalles));
                    parameters.put("movimientosDS", new JRBeanCollectionDataSource((Collection<?>) movimientos));
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, parameters, new JREmptyDataSource());
                    byte[] binaryReport = JasperExportManager.exportReportToPdf(jasperPrint);
                    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                            .filename("sediReport_" + new java.util.Date().getTime() + ".pdf")
                            .build();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDisposition(contentDisposition);
                    return ResponseEntity.ok().contentLength(binaryReport.length)
                            .headers(headers)
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(new ByteArrayResource(binaryReport));
                } else {
                    //System.err.println("esta caja no tiene una apertura con la fecha especificada");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("esta caja no tiene una apertura con la fecha especificada");
                }
            } else {
                //System.err.println("caja no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("caja no encontrada");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public GenericResponse<Iterable<Apertura>> getAperturas(int idCaja) {
        if (repository.existsById(idCaja)) {
            return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, aperturaRepository.findAllByCajaId(idCaja));
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "caja no encontrada");
        }
    }

    public GenericResponse<Iterable<DetalleCaja>> getCurrentDetails(int idCaja) {
        if (repository.existsById(idCaja)) {
            Iterable<DetalleCaja> detalles = detalleCajaRepository.findAllByCajaIdAndCerradoIsFalse(idCaja);
            if (!IterableUtils.isEmpty(detalles)) {
                return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, detalles);
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no hay detalles actuales para esta caja,es posible que la caja esté cerrada");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "caja no encontrada");
        }
    }

    public GenericResponse<Iterable<MovCaja>> getMovimientosById(int idCaja) {
        if (repository.existsById(idCaja)) {
            return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, movCajaRepository.findAllByCajaId(idCaja));
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "caja no encontrada");
        }
    }

    public GenericResponse<Iterable<MovCaja>> getMovimientosByAperturaId(int idApertura) {
        Optional<Apertura> optApertura = aperturaRepository.findById(idApertura);
        return optApertura.map(apertura -> new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, movCajaRepository.findAllByApertura(apertura))).orElseGet(() -> new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "apertura no encontrada"));
    }

    public GenericResponse<Iterable<DetalleCaja>> getUltimosDetalles(int idCaja) {
        if (repository.existsById(idCaja)) {
            return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, detalleCajaRepository.listUltimosDetalles(idCaja));
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "caja no encontrada");
        }
    }
}
