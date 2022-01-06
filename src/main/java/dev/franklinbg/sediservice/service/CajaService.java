package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.*;
import dev.franklinbg.sediservice.entity.dto.CajaWithDetallesDTO;
import dev.franklinbg.sediservice.repository.*;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CajaService {
    private final CajaRepository repository;
    private final DetalleCajaRepository detalleCajaRepository;
    private final MetodoPagoRepsository metodoPagoRepsository;
    private final MovCajaRepository movCajaRepository;
    private final ConceptoMovCajaRepository conceptoMovCajaRepository;

    public CajaService(CajaRepository repository, DetalleCajaRepository detalleCajaRepository, MetodoPagoRepsository metodoPagoRepsository, MovCajaRepository movCajaRepository, ConceptoMovCajaRepository conceptoMovCajaRepository) {
        this.repository = repository;
        this.detalleCajaRepository = detalleCajaRepository;
        this.metodoPagoRepsository = metodoPagoRepsository;
        this.movCajaRepository = movCajaRepository;
        this.conceptoMovCajaRepository = conceptoMovCajaRepository;
    }

    public GenericResponse<Caja> findByUsuarioId(int idU) {
        Optional<Caja> optCaja = repository.findByUsuarioId(idU);
        return optCaja.map(caja -> new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, caja)).orElseGet(() -> new GenericResponse<>(TIPO_RESULT, RPTA_OK, "no se ha encontrado una caja asociada a este usuario"));
    }

    public GenericResponse<CajaWithDetallesDTO> open(CajaWithDetallesDTO dto) {
        Optional<Caja> optCaja = repository.findById(dto.getIdCaja());
        if (optCaja.isPresent()) {
            detalleCajaRepository.saveAll(dto.getDetalles());
            double montoApertura = 0.0;
            for (DetalleCaja detalle : dto.getDetalles()) {
                montoApertura += detalle.getMonto();
            }
            Caja caja = optCaja.get();
            caja.setMontoApertura(montoApertura);
            caja.setMontoCierre(montoApertura);
            caja.setEstado('A');
            caja.setFechaApertura(new Date());
            repository.save(caja);
            return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "caja abierta correctamente", dto);
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
                if (movCaja.getTipoMov() == 'E') {
                    caja.setMontoCierre(caja.getMontoCierre() + movCaja.getTotal());
                } else {
                    if (movCaja.getTotal() > caja.getMontoCierre()) {
                        return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "salida rechazada,el monto excede el monto actualmente disponible en caja,disponible:" + caja.getMontoCierre());
                    }
                    caja.setMontoCierre(caja.getMontoCierre() - movCaja.getTotal());
                }
                Optional<MetodoPago> optionalMetodoPago = metodoPagoRepsository.findById(movCaja.getMetodoPago().getId());
                if (optionalMetodoPago.isPresent()) {
                    MetodoPago metodoPago = optionalMetodoPago.get();
                    Optional<ConceptoMovCaja> optionalConceptoMovCaja = conceptoMovCajaRepository.findById(movCaja.getConceptoMovCaja().getId());
                    if (optionalConceptoMovCaja.isPresent()) {
                        Optional<DetalleCaja> optionalDetalleCaja = detalleCajaRepository.findByCajaIdAndMetodoPagoId(caja.getId(), metodoPago.getId());
                        if (optionalDetalleCaja.isPresent()) {
                            DetalleCaja detalleCaja = optionalDetalleCaja.get();
                            if (movCaja.getTipoMov() == 'E') {
                                detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() + movCaja.getTotal());
                            } else {
                                if (movCaja.getTotal() > detalleCaja.getMontoCierre()) {
                                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "salida rechazada,el monto excede el monto actualmente disponible en este metodo de pago,disponible:" + detalleCaja.getMontoCierre());
                                }
                                detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() - movCaja.getTotal());
                            }
                            repository.save(caja);
                            detalleCajaRepository.save(detalleCaja);
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
            Optional<DetalleCaja> optionalDetalleCaja = detalleCajaRepository.findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(movCaja.getCaja().getId(), movCaja.getMetodoPago().getId());
            if (optionalDetalleCaja.isPresent()) {
                DetalleCaja detalleCaja = optionalDetalleCaja.get();
                if (movCaja.getTipoMov() == 'E') {
                    detalleCaja.setMontoCierre(caja.getMontoCierre() - movCaja.getTotal());
                    caja.setMontoCierre(caja.getMontoCierre() - movCaja.getTotal());
                } else {
                    caja.setMontoCierre(caja.getMontoCierre() + movCaja.getTotal());
                    detalleCaja.setMontoCierre(caja.getMontoCierre() + movCaja.getTotal());
                }
                repository.save(caja);
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "movimiento anulado correctamente", movCajaRepository.save(movCaja));
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontró el detalle de caja a actualizar");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no se encontró el movimiento a anular");
        }
    }
}
