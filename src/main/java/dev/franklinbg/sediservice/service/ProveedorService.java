package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Proveedor;
import dev.franklinbg.sediservice.repository.ProveedorRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class ProveedorService {
    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Iterable<Proveedor>> listActivos() {
        return new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repository.listActivos());
    }
}
