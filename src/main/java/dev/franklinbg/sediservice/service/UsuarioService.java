package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Usuario;
import dev.franklinbg.sediservice.repository.UsuarioRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.franklinbg.sediservice.utils.Global.*;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Usuario> login(String email, String password) {
        Optional<Usuario> optUsuario = repository.findByCorreoAndContrasenia(email, password);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return new GenericResponse<>(TIPO_AUTH, RPTA_OK, "Bienvenido al sistema:" + usuario.getNombre(), usuario);
        } else {
            return new GenericResponse<>(TIPO_AUTH, RPTA_WARNING, "No se encontró al usuario");
        }
    }

    public GenericResponse<Iterable<Usuario>> listAll() {
        return new GenericResponse<>(TIPO_AUTH, RPTA_OK, OPERACION_CORRECTA, repository.findAll());
    }
}