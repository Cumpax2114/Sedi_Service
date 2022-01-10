package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.ConceptoMovCaja;
import dev.franklinbg.sediservice.repository.ConceptoMovCajaRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class ConceptoMovCajaService {
    private final ConceptoMovCajaRepository repository;

    public ConceptoMovCajaService(ConceptoMovCajaRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Iterable<ConceptoMovCaja>> listActivos() {
        return new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repository.listActivos());
    }
}
