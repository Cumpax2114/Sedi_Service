package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.DetalleCaja;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DetalleCajaRepository extends CrudRepository<DetalleCaja, Integer> {
    Optional<DetalleCaja> findByCajaIdAndMetodoPagoId(int cajaId, int metodoPagoId);

    Optional<DetalleCaja> findByCajaIdAndMetodoPagoIdAndCerradoIsFalse(int cajaId, int metodoPagoId);
}