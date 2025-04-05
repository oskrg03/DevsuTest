package com.account.service;

import com.account.dto.CuentaCreateDTO;
import com.account.dto.CuentaUpdateDTO;
import com.account.mapper.CuentaMapper;
import com.account.model.Cuenta;
import com.account.repository.CuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con ID: " + id));

    }

    public Cuenta crearCuenta(CuentaCreateDTO cuentaCreateDTO) {
        Cuenta cuenta = CuentaMapper.INSTANCE.cuentaDTOToCuenta(cuentaCreateDTO);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizarCuenta(Long id, CuentaUpdateDTO cuentaActualizada) {
        Cuenta cuenta = obtenerPorId(id);
        cuenta.setNumeroCuenta(Optional.ofNullable(cuentaActualizada.getNumeroCuenta()).orElse(cuenta.getNumeroCuenta()));
        cuenta.setTipoCuenta(Optional.ofNullable(cuentaActualizada.getTipoCuenta()).orElse(cuenta.getTipoCuenta()));
        cuenta.setEstado(Optional.ofNullable(cuentaActualizada.getEstado()).orElse(cuenta.getEstado()));
        cuenta.setSaldoActual(Optional.ofNullable(cuentaActualizada.getSaldoActual()).orElse(cuenta.getSaldoActual()));
        return cuentaRepository.save(cuenta);
    }

}