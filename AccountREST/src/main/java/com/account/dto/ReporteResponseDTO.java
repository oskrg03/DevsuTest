package com.account.dto;

import com.account.model.Movimiento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReporteResponseDTO {

    private String nombreCliente;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private Boolean estado;

    private List<Movimiento> movimientos;
}
