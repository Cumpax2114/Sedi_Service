package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Usuario;
import dev.franklinbg.sediservice.service.UsuarioService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("login")
    public GenericResponse<Usuario> login(@RequestParam String email, @RequestParam String password) {
        return service.login(email, password);
    }
}
