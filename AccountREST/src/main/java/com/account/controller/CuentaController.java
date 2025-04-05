package com.account.controller;

import com.account.dto.CuentaCreateDTO;
import com.account.dto.CuentaUpdateDTO;
import com.account.model.Cuenta;
import com.account.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    @Operation(summary = "Obtener listado de cuentas", description = "Obtiene un listado de las cuentas existentes")
    public List<Cuenta> obtenerTodas() {
        return cuentaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cuenta según su id", description = "Obtiene un la información de una cuenta según su id")
    public Cuenta obtenerPorId(@PathVariable Long id) {
        return cuentaService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear una cuenta", description = "Crea una cuenta según los datos suministrados")
    public ResponseEntity<Cuenta> crear(@RequestBody CuentaCreateDTO cuenta) {
        return new ResponseEntity<>(cuentaService.crearCuenta(cuenta), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar una cuenta", description = "Actualiza algunos datos de una cuenta")
    public Cuenta actualizar(@PathVariable Long id, @RequestBody CuentaUpdateDTO cuenta) {
        return cuentaService.actualizarCuenta(id, cuenta);
    }

}