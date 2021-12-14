package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Cliente;
import dev.franklinbg.sediservice.repsoitory.ClienteRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import dev.franklinbg.sediservice.utils.Global;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Cliente> save(Cliente cliente) {
        if (repository.existsByDocumento(cliente.getDocumento())) {
            return new GenericResponse<>(Global.TIPO_RESULT, Global.RPTA_WARNING, "ya existe un cliente con el mismo documento", null);
        } else {
            return new GenericResponse<>(Global.TIPO_RESULT, Global.RPTA_OK, "cliente registrado correctamente", repository.save(cliente));
        }
    }
}
