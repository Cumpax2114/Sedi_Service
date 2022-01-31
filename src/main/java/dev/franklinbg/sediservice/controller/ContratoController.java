package dev.franklinbg.sediservice.controller;

import dev.franklinbg.sediservice.entity.Contrato;
import dev.franklinbg.sediservice.entity.PagoContrato;
import dev.franklinbg.sediservice.service.ContratoService;
import dev.franklinbg.sediservice.utils.GenericResponse;
import dev.franklinbg.sediservice.utils.Global;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

import static dev.franklinbg.sediservice.utils.Global.RPTA_WARNING;
import static dev.franklinbg.sediservice.utils.Global.TIPO_RESULT;

@RestController
@RequestMapping("contrato")
public class ContratoController {
    private final ContratoService service;

    public ContratoController(ContratoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse<Iterable<Contrato>> listAll() {
        return service.listAll();
    }

    @PostMapping
    public GenericResponse<Contrato> save(@Valid @RequestBody Contrato contrato) {
        return service.save(contrato);
    }

    @PostMapping("pagar")
    public GenericResponse<Iterable<PagoContrato>> pagar(@RequestBody ArrayList<@Valid PagoContrato> pagos) {
        if (pagos.isEmpty()) {
            return new GenericResponse<>(TIPO_RESULT, RPTA_WARNING, "no hay pagos");
        } else {
            return service.pagar(pagos);
        }

    }
}
