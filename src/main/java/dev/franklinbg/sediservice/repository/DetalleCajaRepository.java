package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.DetalleCaja;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Optional;

public interface DetalleCajaRepository extends CrudRepository<DetalleCaja, Integer> {
    Optional<DetalleCaja> findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(int cajaId, int metodoPagoId);

    Optional<DetalleCaja> findByCajaIdAndMetodoPagoIdAndFechaCreacion(int cajaId, int metodoPagoId, Date fechaCreacion);

    Iterable<DetalleCaja> findAllByCajaIdAndFechaCreacion(int cajaId, Date fechaCreacion);

    Iterable<DetalleCaja> findAllByCajaIdAndCerradoIsFalse(int cajaId);

    @Query(value = "SELECT * FROM detalle_caja WHERE caja_id=:idCaja AND cerrado IS TRUE ORDER BY id DESC LIMIT 3", nativeQuery = true)
    Iterable<DetalleCaja> listUltimosDetalles(int idCaja);
}