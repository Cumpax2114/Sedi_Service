package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.ConceptoMovCaja;
import dev.franklinbg.sediservice.service.ConceptoMovCajaService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conceptoMcaja")
public class ConceptoMovCajaController {
    private final ConceptoMovCajaService service;

    public ConceptoMovCajaController(ConceptoMovCajaService service) {
        this.service = service;
    }

    @GetMapping("activos")
    public GenericResponse<Iterable<ConceptoMovCaja>> listActivos() {
        return service.listActivos();
    }
}
