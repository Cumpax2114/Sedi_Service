package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Apertura;
import dev.franklinbg.sediservice.entity.Caja;
import dev.franklinbg.sediservice.entity.DetalleCaja;
import dev.franklinbg.sediservice.entity.MovCaja;
import dev.franklinbg.sediservice.entity.dto.CajaWithDetallesDTO;
import dev.franklinbg.sediservice.service.CajaService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("caja")
public class CajaController {
    private final CajaService service;

    public CajaController(CajaService service) {
        this.service = service;
    }

    @GetMapping("byusuarioid/{idU}")
    public GenericResponse<Caja> getByUserId(@PathVariable int idU) {
        return service.findByUsuarioId(idU);
    }

    @PostMapping("open")
    public GenericResponse<CajaWithDetallesDTO> open(@RequestBody CajaWithDetallesDTO dto) {
        return service.open(dto);
    }

    @PostMapping("close")
    public GenericResponse<Iterable<DetalleCaja>> close(@RequestParam int idCaja) {
        return service.close(idCaja);
    }

    @PostMapping("movimiento")
    public GenericResponse<MovCaja> saveMovimiento(@Valid @RequestBody MovCaja movCaja) {
        return service.saveMovimiento(movCaja);
    }

    @DeleteMapping("movimiento/{id}")
    public GenericResponse<MovCaja> anularMovimiento(@PathVariable int id) {
        return service.anularMovimiento(id);
    }

    @GetMapping("report")
    public ResponseEntity<?> report(@RequestParam int idCaja, @RequestParam String fechaApertura) {
        return service.descargarReporte(idCaja, fechaApertura);
    }

    @GetMapping("aperturas/{idCaja}")
    public GenericResponse<Iterable<Apertura>> getAperturas(@PathVariable int idCaja) {
        return service.getAperturas(idCaja);
    }

    @GetMapping("detallesActuales/{idCaja}")
    public GenericResponse<Iterable<DetalleCaja>> getCurrentDetails(@PathVariable int idCaja) {
        return service.getCurrentDetails(idCaja);
    }
}
