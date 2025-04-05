package com.account.service;

import com.account.dto.MovimientoDTO;
import com.account.exception.CuentaNoExisteException;
import com.account.exception.SaldoInsuficienteException;
import com.account.model.Cuenta;
import com.account.model.Movimiento;
import com.account.repository.CuentaRepository;
import com.account.repository.MovimientoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    private final CuentaRepository cuentaRepository;

    private final MovimientoRepository movimientoRepository;

    public MovimientoService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public Movimiento registrarMovimiento(MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new CuentaNoExisteException("Cuenta no encontrada", HttpStatus.BAD_REQUEST));

        if (movimientoDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
            if (cuenta.getSaldoActual().compareTo(movimientoDTO.getValor().abs()) < 0) {
                throw new SaldoInsuficienteException("Saldo no disponible", HttpStatus.BAD_REQUEST);
            }
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setSaldo(cuenta.getSaldoActual().add(movimientoDTO.getValor()));
        movimiento.setDescripcion(movimientoDTO.getDescripcion());
        movimiento.setCuenta(cuenta);
        cuenta.setSaldoActual(cuenta.getSaldoActual().add(movimientoDTO.getValor()));

        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> obtenerMovimientos(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    public Optional<Movimiento> obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id);
    }
}
