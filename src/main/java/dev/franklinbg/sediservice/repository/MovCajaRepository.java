package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.MovCaja;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface MovCajaRepository extends CrudRepository<MovCaja, Integer> {
    Iterable<MovCaja> findAllByCajaId(int cajaId);

    Iterable<MovCaja> findAllByCajaIdAndAperturaFechaApertura(int cajaId, Date fechaApertura);
}
