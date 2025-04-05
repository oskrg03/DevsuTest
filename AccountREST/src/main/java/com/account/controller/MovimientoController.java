package com.account.controller;

import com.account.dto.MovimientoDTO;
import com.account.exception.CuentaNoExisteException;
import com.account.exception.SaldoInsuficienteException;
import com.account.model.Movimiento;
import com.account.service.MovimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }


    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<Movimiento>> obtenerMovimientos(@PathVariable Long cuentaId) {
        List<Movimiento> movimientos = movimientoService.obtenerMovimientos(cuentaId);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimiento(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.obtenerMovimientoPorId(id);
        return movimiento.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> registrarMovimiento(@RequestBody MovimientoDTO movimiento) {
        try {
            Movimiento movimientoRegistrado = movimientoService.registrarMovimiento(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
        } catch (SaldoInsuficienteException | CuentaNoExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}