package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.DetalleCaja;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Optional;

public interface DetalleCajaRepository extends CrudRepository<DetalleCaja, Integer> {
    Optional<DetalleCaja> findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(int cajaId, int metodoPagoId);

    Optional<DetalleCaja> findByCajaIdAndMetodoPagoIdAndFechaCreacion(int cajaId, int metodoPagoId, Date fechaCreacion);

    Iterable<DetalleCaja> findAllByCajaIdAndFechaCreacion(int cajaId, Date fechaCreacion);

    Iterable<DetalleCaja> findAllByCajaIdAndCerradoIsFalse(int cajaId);
}