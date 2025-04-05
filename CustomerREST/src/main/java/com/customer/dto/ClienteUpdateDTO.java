package com.customer.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;

    @NotNull
    private String genero;

    @NotNull
    private Integer edad;

    @NotNull
    private String identificacion;

    @NotNull
    private String direccion;

    @NotNull
    private String telefono;

    @NotNull
    private Boolean estado;

    @NotNull
    private String contrasena;

}
