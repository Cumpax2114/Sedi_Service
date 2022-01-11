package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Cliente;
import dev.franklinbg.sediservice.repository.ClienteRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Cliente> save(Cliente cliente) {
        if (repository.existsByDocumento(cliente.getDocumento())) {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "ya existe un cliente con el mismo documento");
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "cliente registrado correctamente", repository.save(cliente));
        }
    }

    public GenericResponse<Iterable<Cliente>> listAll() {
        return new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repository.findAll());
    }
}
