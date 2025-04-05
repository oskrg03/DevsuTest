package com.account.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private LocalDateTime fechaMovimiento;
    private String tipoMovimiento;
    private BigDecimal saldo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Cuenta cuenta;

    @PrePersist
    public void prePersist() {
        this.fechaMovimiento = LocalDateTime.now();
    }
}
