package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.TipoContrato;
import dev.franklinbg.sediservice.repository.TipoContratoRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import dev.franklinbg.sediservice.utils.Global;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.OPERACION_CORRECTA;
import static dev.franklinbg.sediservice.utils.Global.RPTA_OK;

@Service
@Transactional
public class TipoContratoService {
    private final TipoContratoRepository repository;

    public TipoContratoService(TipoContratoRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Iterable<TipoContrato>> listActivos() {
        return new GenericResponse<>(Global.TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, repository.listActivos());
    }
}
