package com.customer.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCreateDTO extends ClienteUpdateDTO {

    @NotNull
    private String clienteId;

}
