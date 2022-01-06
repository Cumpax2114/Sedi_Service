package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Cliente;
import dev.franklinbg.sediservice.service.ClienteService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("cliente")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }
@GetMapping
public GenericResponse<Iterable<Cliente>>listAll(){
        return service.listAll();
}
    @PostMapping
    public GenericResponse<Cliente> save(@Valid @RequestBody Cliente cliente) {
        return service.save(cliente);
    }
}
