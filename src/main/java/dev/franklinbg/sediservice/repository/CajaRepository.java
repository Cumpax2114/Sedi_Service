package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.Caja;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CajaRepository extends CrudRepository<Caja, Integer> {
    Optional<Caja> findByUsuarioId(int usuarioId);
}
