package dev.franklinbg.sediservice.service;

import dev.franklinbg.sediservice.entity.Caja;
import dev.franklinbg.sediservice.entity.DetalleCaja;
import dev.franklinbg.sediservice.entity.dto.CajaWithDetallesDTO;
import dev.franklinbg.sediservice.repository.CajaRepository;
import dev.franklinbg.sediservice.repository.DetalleCajaRepository;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.franklinbg.sediservice.utils.Global.*;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CajaService {
    private final CajaRepository repository;
    private final DetalleCajaRepository detalleCajaRepository;

    public CajaService(CajaRepository repository, DetalleCajaRepository detalleCajaRepository) {
        this.repository = repository;
        this.detalleCajaRepository = detalleCajaRepository;
    }

    public GenericResponse<Caja> findByUsuarioId(int idU) {
        Optional<Caja> optCaja = repository.findByUsuarioId(idU);
        return optCaja.map(caja -> new GenericResponse<>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, caja)).orElseGet(() -> new GenericResponse<>(TIPO_RESULT, RPTA_OK, "no se ha encontrado una caja asociada a este usuario"));
    }

    public GenericResponse<CajaWithDetallesDTO> open(CajaWithDetallesDTO dto) {
        Optional<Caja> optCaja = repository.findById(dto.getIdCaja());
        if (optCaja.isPresent()) {
            detalleCajaRepository.saveAll(dto.getDetalles());
            double montoApertura = 0.0;
            for (DetalleCaja detalle : dto.getDetalles()) {
                montoApertura += detalle.getMonto();
            }
            Caja caja = optCaja.get();
            caja.setMontoApertura(montoApertura);
            caja.setEstado('A');
            caja.setFechaApertura(new Date());
            repository.save(caja);
            return new GenericResponse<>(TIPO_RESULT, RPTA_OK, "caja abierta correctamente",dto);
        } else {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING,"no se encontro la caja");
        }
    }
}
