package com.customer.controller;

import com.customer.dto.ClienteCreateDTO;
import com.customer.dto.ClienteUpdateDTO;
import com.customer.model.Cliente;
import com.customer.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class CustomerController {


    private final ClienteService clienteService;

    public CustomerController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener información de cliente", description = "Obtiene información de un cliente según su Id")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {

        return ResponseEntity.ok(clienteService.getById(id));
    }

    @PostMapping
    @Operation(summary = "crear un cliente", description = "Crea un cliente según los datos suministrados")
    public Cliente createCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) {
        return clienteService.crearCliente(clienteCreateDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "actualizar un cliente", description = "actualiza los datos de un cliente")
    public Cliente updateCliente(@PathVariable String id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        return clienteService.actualizarCliente(id, clienteUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar un cliente", description = "elimina un cliente")
    public String deleteCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }
}
