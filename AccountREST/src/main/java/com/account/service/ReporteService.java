package com.account.service;

import com.account.app.ClienteService;
import com.account.dto.ReporteResponseDTO;
import com.account.model.Movimiento;
import com.account.repository.CuentaRepository;
import com.account.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ClienteService customerService;


    public ReporteService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository, ClienteService customerService) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.customerService = customerService;
    }

    public Mono<List<ReporteResponseDTO>> generarReporte(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Primero obtenemos las cuentas para el cliente.
        return Mono.just(cuentaRepository.findByClienteId(clienteId))
                .flatMap(cuentas -> {
                    // Para cada cuenta, obtendremos los movimientos y la información del cliente
                    List<Mono<ReporteResponseDTO>> reporteList = cuentas.stream().map(cuenta -> {
                        // Obtener los movimientos para la cuenta
                        List<Movimiento> movimientos = movimientoRepository
                                .findByCuenta_ClienteIdAndCuentaIdAndFechaMovimientoBetween(cuenta.getClienteId(), cuenta.getId(), fechaInicio, fechaFin);

                        // Crear el ReporteResponseDTO inicial
                        ReporteResponseDTO reporteDTO = new ReporteResponseDTO();
                        reporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
                        reporteDTO.setTipoCuenta(cuenta.getTipoCuenta());
                        reporteDTO.setSaldoActual(cuenta.getSaldoActual());
                        reporteDTO.setEstado(cuenta.getEstado());
                        reporteDTO.setMovimientos(movimientos);
                        reporteDTO.setFechaCreacion(cuenta.getFechaCreacion());
                        reporteDTO.setFechaActualizacion(cuenta.getFechaActualizacion());

                        // Obtener la información del cliente de manera reactiva
                        return customerService.obtenerInformacionCliente(clienteId)
                                .map(customerDTO -> {
                                    reporteDTO.setNombreCliente(customerDTO.getNombre());
                                    return reporteDTO;
                                });
                    }).collect(Collectors.toList());

                    // Combinar todos los Monos en un solo Mono con la lista final
                    return Mono.zip(reporteList, results ->
                            Arrays.stream(results)
                                    .map(result -> (ReporteResponseDTO) result)
                                    .collect(Collectors.toList())
                    );
                });
    }
}