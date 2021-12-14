package dev.franklinbg.sediservice.repsoitory;

import dev.franklinbg.sediservice.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    Optional<Cliente> findByDocumento(@Param("documento") String documento);

    boolean existsByDocumento(@Param("documento") String documento);
}
