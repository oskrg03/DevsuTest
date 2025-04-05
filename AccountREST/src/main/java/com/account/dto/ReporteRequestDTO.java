package com.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReporteRequestDTO {

    private String clienteId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;


}