package dev.franklinbg.sediservice.repsoitory;

import dev.franklinbg.sediservice.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Query("SELECT U FROM Usuario U WHERE U.correo=:correo AND U.contrasenia=:password")
    Optional<Usuario> findByUserNameAndPassword(String correo, String password);
}
