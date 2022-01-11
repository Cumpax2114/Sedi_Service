package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.MetodoPago;
import dev.franklinbg.sediservice.service.MetodoPagoService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metodopago")
public class MetodoPagoController {
    private final MetodoPagoService service;

    public MetodoPagoController(MetodoPagoService service) {
        this.service = service;
    }

    @GetMapping("/activos")
    public GenericResponse<Iterable<MetodoPago>> getAll() {
        return service.getAll();
    }
}
