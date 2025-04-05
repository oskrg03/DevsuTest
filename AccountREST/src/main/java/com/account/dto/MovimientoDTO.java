package com.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDTO {
    @NotNull
    private Long cuentaId;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private String tipoMovimiento;
    @NotNull
    private String descripcion;
}
