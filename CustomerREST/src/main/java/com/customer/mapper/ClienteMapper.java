package com.customer.mapper;

import com.customer.dto.ClienteCreateDTO;
import com.customer.dto.ClienteUpdateDTO;
import com.customer.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "contrasena", target = "contrasena")
    Cliente clienteDTOToCliente(ClienteCreateDTO clienteCreateDTO);

    ClienteCreateDTO clienteToClienteDTO(Cliente cliente);

    ClienteCreateDTO clienteUpdateDTOToCliente(ClienteUpdateDTO cliente);
}
