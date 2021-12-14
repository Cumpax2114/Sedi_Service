package dev.franklinbg.sediservice.repsoitory;

import dev.franklinbg.sediservice.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreoAndContrasenia(String correo, String password);
}
