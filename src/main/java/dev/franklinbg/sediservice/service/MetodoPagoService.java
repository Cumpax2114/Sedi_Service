package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.MetodoPago;
import dev.franklinbg.sediservice.repository.MetodoPagoRepsository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class MetodoPagoService {
    private final MetodoPagoRepsository repsository;

    public MetodoPagoService(MetodoPagoRepsository repsository) {
        this.repsository = repsository;
    }

    public GenericResponse<Iterable<MetodoPago>> getAll() {
        return new GenericResponse<>(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, repsository.findAll());
    }
}

