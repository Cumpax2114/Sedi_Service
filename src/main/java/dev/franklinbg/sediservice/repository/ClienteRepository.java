package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    boolean existsByDocumento(String documento);
}
