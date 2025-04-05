package com.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "cuenta")
@Getter
@Setter
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroCuenta;

    private String tipoCuenta;

    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;

    private Boolean estado;

    private Long clienteId;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
