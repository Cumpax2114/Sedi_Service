package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.TipoContrato;
import dev.franklinbg.sediservice.service.TipoContratoService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tipoContrato")
public class TipoContratoController {
    private final TipoContratoService service;

    public TipoContratoController(TipoContratoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse<Iterable<TipoContrato>> getTiposContrato() {
        return service.listActivos();
    }
}
