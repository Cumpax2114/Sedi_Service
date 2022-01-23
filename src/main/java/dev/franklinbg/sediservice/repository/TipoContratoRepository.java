package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.TipoContrato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TipoContratoRepository extends CrudRepository<TipoContrato, Integer> {
    @Query("SELECT TC from TipoContrato  TC WHERE TC.estado='A'")
    Iterable<TipoContrato> listActivos();
}
