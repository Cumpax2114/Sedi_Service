package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    Optional<Cliente> findByDocumento(String documento);

    boolean existsByDocumento(String documento);
}
