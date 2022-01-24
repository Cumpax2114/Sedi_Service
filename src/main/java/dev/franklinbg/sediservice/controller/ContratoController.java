package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Contrato;
import dev.franklinbg.sediservice.service.ContratoService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("contrato")
public class ContratoController {
    private final ContratoService service;

    public ContratoController(ContratoService service) {
        this.service = service;
    }

    @PostMapping
    public GenericResponse<Contrato> save(@Valid @RequestBody Contrato contrato) {
        return service.save(contrato);
    }
}
