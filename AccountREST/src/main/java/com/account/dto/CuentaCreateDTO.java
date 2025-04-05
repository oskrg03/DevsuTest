package com.account.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaCreateDTO{

    @NotNull
    private Long clienteId;
    @NotNull
    private Double saldoInicial;
    @NotNull
    private String numeroCuenta;
    @NotNull
    private String tipoCuenta;
    @NotNull
    private Boolean estado;
    @NotNull
    private Double saldoActual;

}
