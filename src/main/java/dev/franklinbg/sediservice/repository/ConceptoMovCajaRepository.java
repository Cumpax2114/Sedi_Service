package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.ConceptoMovCaja;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ConceptoMovCajaRepository extends CrudRepository<ConceptoMovCaja, Integer> {
    @Query(value = "SELECT * FROM concepto_mov_caja WHERE estado = 'A' ", nativeQuery = true)
    Iterable<ConceptoMovCaja> listActivos();
}
