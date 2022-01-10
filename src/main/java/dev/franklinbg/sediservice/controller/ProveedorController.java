package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Proveedor;
import dev.franklinbg.sediservice.service.ProveedorService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proveedor")
public class ProveedorController {
private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }
    @GetMapping("activos")
    public GenericResponse<Iterable<Proveedor>>listActivos(){
        return service.listActivos();
    }
}
