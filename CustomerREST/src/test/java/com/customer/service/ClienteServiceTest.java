package com.customer.service;

import com.customer.dto.ClienteCreateDTO;
import com.customer.dto.ClienteUpdateDTO;
import com.customer.exception.ClienteNotFoundException;
import com.customer.model.Cliente;
import com.customer.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setClienteId("C1");
        cliente.setNombre("TEST");
        cliente.setEstado(true);
        cliente.setIdentificacion("TEST");
        cliente.setTelefono("TEST");
    }

    @Test
    void testCrearCliente() {
        ClienteCreateDTO clienteDTO = new ClienteCreateDTO();
        clienteDTO.setClienteId("C1");
        clienteDTO.setNombre("TEST");
        clienteDTO.setEstado(true);
        clienteDTO.setIdentificacion("TEST");
        clienteDTO.setTelefono("TEST");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.crearCliente(clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getClienteId(), result.getClienteId());
        assertEquals(clienteDTO.getNombre(), result.getNombre());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testActualizarCliente() {
        ClienteUpdateDTO clienteDTO = new ClienteUpdateDTO();
        clienteDTO.setNombre("TEST TEST");
        clienteDTO.setEstado(false);

        when(clienteRepository.findByClienteId("C1")).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.actualizarCliente("C1", clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getNombre(), result.getNombre());
        assertEquals(clienteDTO.getEstado(), result.getEstado());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testActualizarClienteNoExistente() {
        ClienteUpdateDTO clienteDTO = new ClienteUpdateDTO();
        clienteDTO.setEstado(false);

        when(clienteRepository.findByClienteId("2")).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.actualizarCliente("2", clienteDTO);
        });
    }

    @Test
    void testObtenerClienteExistenteByClienteId() {

        when(clienteRepository.findByClienteId("C1")).thenReturn(Optional.of(cliente));

        ClienteCreateDTO resultado = clienteService.getClienteById("C1");

        assertNotNull(resultado);
        assertEquals("C1", resultado.getClienteId());

        verify(clienteRepository).findByClienteId("C1");
    }

    @Test
    void testObtenerClienteExistenteById() {

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.getById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(clienteRepository).findById(1L);
    }

    @Test
    public void testEliminarCliente_ClienteExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        String resultado = clienteService.eliminarCliente(1L);

        assertEquals(resultado, "Cliente eliminado con éxito.");
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarCliente_ClienteNoExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        String resultado = clienteService.eliminarCliente(1L);

        assertEquals(resultado, "Cliente no encontrado.");
    }


}