package com.account.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaUpdateDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private Boolean estado;
    private BigDecimal saldoActual;

}
