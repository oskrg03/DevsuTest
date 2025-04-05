package com.account.repository;

import com.account.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);
    List<Movimiento> findByCuenta_ClienteIdAndCuentaIdAndFechaMovimientoBetween(Long clienteId, Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}