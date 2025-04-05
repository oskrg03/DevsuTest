package com.account.controller;

import com.account.dto.ReporteResponseDTO;
import com.account.service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

/*    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerReportes(
            @RequestParam Long clienteId,
            @RequestParam String fechaInicioStr,
            @RequestParam String fechaFinStr) {
        LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr);
        LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr);
        List<ReporteResponseDTO> reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }*/

    @GetMapping
    public Mono<ResponseEntity<List<ReporteResponseDTO>>> obtenerReportes(
            @RequestParam Long clienteId,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {

        // Llamamos al servicio reactivo, que retorna Mono<List<ReporteResponseDTO>>
        return reporteService.generarReporte(clienteId, fechaInicio, fechaFin)
                .map(reporte -> ResponseEntity.ok(reporte))  // Mapeamos el resultado a un ResponseEntity
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Manejo de casos donde no hay reporte
    }
}
