package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Cliente;
import dev.franklinbg.sediservice.service.ClienteService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cliente")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public GenericResponse<Cliente> save(@RequestBody Cliente cliente) {
        return service.save(cliente);
    }
}
