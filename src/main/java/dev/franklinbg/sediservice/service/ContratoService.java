package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Contrato;
import dev.franklinbg.sediservice.repository.ClienteRepository;
import dev.franklinbg.sediservice.repository.ContratoRepository;
import dev.franklinbg.sediservice.repository.TipoContratoRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class ContratoService {
    private final ContratoRepository repository;
    private final TipoContratoRepository tipoContratorepository;
    private final ClienteRepository clienteRepository;

    public ContratoService(ContratoRepository repository, TipoContratoRepository tipoContratorepository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.tipoContratorepository = tipoContratorepository;
        this.clienteRepository = clienteRepository;
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
}
