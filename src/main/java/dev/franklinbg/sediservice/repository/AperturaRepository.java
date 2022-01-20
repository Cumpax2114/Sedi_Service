package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.Apertura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface AperturaRepository extends CrudRepository<Apertura, Integer> {
    @Query(value = "SELECT * FROM apertura ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Apertura getLastRegister();

    boolean existsByCajaIdAndFechaApertura(int cajaId, Date fechaApertura);

    Iterable<Apertura> findAllByCajaId(int idCaja);
}
