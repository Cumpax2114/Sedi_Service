package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.*;
import dev.franklinbg.sediservice.repository.*;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class ContratoService {
    private final ContratoRepository repository;
    private final TipoContratoRepository tipoContratorepository;
    private final ClienteRepository clienteRepository;
    private final CajaRepository cajaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final DetalleCajaRepository detalleCajaRepository;
    private final PagoContratoRepository pagoContratoRepository;

    public ContratoService(ContratoRepository repository, TipoContratoRepository tipoContratorepository, ClienteRepository clienteRepository, CajaRepository cajaRepository, MetodoPagoRepository metodoPagoRepository, DetalleCajaRepository detalleCajaRepository, PagoContratoRepository pagoContratoRepository) {
        this.repository = repository;
        this.tipoContratorepository = tipoContratorepository;
        this.clienteRepository = clienteRepository;
        this.cajaRepository = cajaRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.detalleCajaRepository = detalleCajaRepository;
        this.pagoContratoRepository = pagoContratoRepository;
    }

    public GenericResponse<Contrato> save(Contrato contrato) {
        if (tipoContratorepository.existsById(contrato.getTipoContrato().getId())) {
            if (clienteRepository.existsById(contrato.getCliente().getId())) {
                if (contrato.getFechaTermino().after(contrato.getFechaInicio())) {
                    contrato.setCuotasPagadas(0);
                    contrato.setEstado('P');
                    return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "Contrato registrado correctamente", repository.save(contrato));
                } else {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "la fecha de término debe estar después de la fecha de inicio");
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "cliente no encontrado");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "tipo de contrato no encontrado");
        }
    }

    public GenericResponse<Iterable<Contrato>> listAll() {
        return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, repository.findAll());
    }

    public GenericResponse<Iterable<PagoContrato>> pagar(ArrayList<PagoContrato> pagos) {
        int index = 0;
        int initialIdContrato = 0;
        int initalIdCaja = 0;
        int initialIdMetodoPago = 0;
        double initialMontoPagado = 0.0;
        for (PagoContrato pago : pagos) {
            if (index == 0) {
                initialIdContrato = pago.getContrato().getId();
                initalIdCaja = pago.getCaja().getId();
                initialIdMetodoPago = pago.getMetodoPago().getId();
                initialMontoPagado = 0.00;
            } else {
                if (pago.getContrato().getId() != initialIdContrato) {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "para usar este metodo todos los id's de contrato de la lista deben ser iguales");
                }
                if (pago.getCaja().getId() != initalIdCaja) {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "para usar este metodo todos los id's de caja de la lista deben ser iguales");
                }
                if (pago.getMetodoPago().getId() != initialIdMetodoPago) {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "para usar este metodo todos los id's de metodo de pago de la lista deben ser iguales");
                }
                if (pago.getMontoPagado() == initialMontoPagado) {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "para usar este metodo todos los montos de pago de la lista deben ser iguales");
                }
            }
            index++;
        }
        for (PagoContrato pago : pagos) {
            Optional<Caja> optionalCaja = cajaRepository.findById(pago.getCaja().getId());
            if (optionalCaja.isPresent()) {
                pago.setCaja(optionalCaja.get());
                Optional<MetodoPago> optionalMetodoPago = metodoPagoRepository.findById(pago.getMetodoPago().getId());
                if (optionalMetodoPago.isPresent()) {
                    pago.setMetodoPago(optionalMetodoPago.get());
                    Optional<Contrato> optionalContrato = repository.findById(pago.getContrato().getId());
                    if (optionalContrato.isPresent()) {
                        pago.setContrato(optionalContrato.get());
                        pago.setFechaPago(new Date());
                    } else {
                        return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "la lista contiene un contrato no válido,operación cancelada");
                    }
                } else {
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "la lista contiene un método de pago no válido,operación cancelada");
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "la lista contiene una caja no válida,operación cancelada");
            }
        }
        PagoContrato pago = pagos.get(0);
        int cuotasPagadas = pago.getContrato().getCuotasPagadas();
        int newCuotasPagadas = 0;
        if (cuotasPagadas == 0) {
            if (pagos.size() <= pago.getContrato().getTotalCuotas()) {
                for (int i = 1; i <= pagos.size(); i++) {
                    pagos.get(i).setNumeroCuota(i);
                    newCuotasPagadas++;
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "estas ingresando mas pagos que la cantidad de cuotas del contrato,operacion cancelada");
            }
        } else {
            int cuotasPorPagar = pago.getContrato().getTotalCuotas() - pago.getContrato().getCuotasPagadas();
            newCuotasPagadas = pago.getContrato().getCuotasPagadas();
            if (pagos.size() <= cuotasPorPagar) {
                for (int i = 1; i <= pagos.size(); i++) {
                    pagos.get(i).setNumeroCuota(cuotasPagadas + 1);
                    newCuotasPagadas++;
                    cuotasPagadas++;
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "estas ingresando mas pagos que la cantidad de cuotas del contrato,operacion cancelada");
            }
        }
        if (pago.getCaja().getEstado() == 'A') {
            Optional<DetalleCaja> optionalDetalleCaja = detalleCajaRepository.findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(pago.getCaja().getId(), pago.getMetodoPago().getId());
            if (optionalDetalleCaja.isPresent()) {
                DetalleCaja detalleCaja = optionalDetalleCaja.get();
                Caja caja = detalleCaja.getCaja();
                double total = pago.getMontoPagado() * pagos.size();
                if(total<=detalleCaja.getMontoCierre()){
                    detalleCaja.setMontoCierre(detalleCaja.getMontoCierre() - total);
                    caja.setMontoCierre(caja.getMontoCierre() - total);
                    detalleCajaRepository.save(detalleCaja);
                    cajaRepository.save(caja);
                    Contrato contrato = pago.getContrato();
                    contrato.setCuotasPagadas(newCuotasPagadas);
                    repository.save(contrato);
                    return new GenericResponse<>(TIPO_RESULT,RPTA_OK,OPERACION_CORRECTA,pagoContratoRepository.saveAll(pagos));
                }else{
                    return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no hay suficiente dinero para procesar la operación:disponible");
                }
            } else {
                return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "detalle de caja no encontrado,es probable que la caja esté cerrada");
            }
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "pago rechazado,la caja esta cerrada");
        }
    }
}
