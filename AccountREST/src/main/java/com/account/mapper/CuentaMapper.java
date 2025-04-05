package com.account.mapper;

import com.account.dto.CuentaCreateDTO;
import com.account.dto.CuentaUpdateDTO;
import com.account.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    Cuenta cuentaDTOToCuenta(CuentaCreateDTO clienteCreateDTO);

    CuentaCreateDTO cuentaToCuentaDTO(Cuenta cliente);

    CuentaCreateDTO cuentaUpdateDTOToCuenta(CuentaUpdateDTO cliente);
}
