package com.customer.service;

import com.customer.dto.ClienteCreateDTO;
import com.customer.dto.ClienteUpdateDTO;
import com.customer.exception.ClienteNotFoundException;
import com.customer.mapper.ClienteMapper;
import com.customer.model.Cliente;
import com.customer.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private  ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public Cliente getById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID "+id+ " no encontrado", HttpStatus.BAD_REQUEST));


    }

    public ClienteCreateDTO getClienteById(String clientId) {
        Optional<Cliente> cliente = Optional.ofNullable(clienteRepository.findByClienteId(clientId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID "+clientId+ " no encontrado", HttpStatus.BAD_REQUEST)));

        return ClienteMapper.INSTANCE.clienteToClienteDTO(cliente.get());

    }

    public Cliente crearCliente(ClienteCreateDTO clienteCreateDTO) {
        Cliente cliente = ClienteMapper.INSTANCE.clienteDTOToCliente(clienteCreateDTO);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(String clientId, ClienteUpdateDTO clienteUpdateDTO) {
        Optional<Cliente> cliente = clienteRepository.findByClienteId(clientId);

        if(cliente.isEmpty()){
            throw new ClienteNotFoundException("Cliente con ID "+clientId+ " no encontrado", HttpStatus.BAD_REQUEST);
        }
        if (!cliente.get().getEstado()) {
            throw new ClienteNotFoundException("El cliente está inactivo y no puede modificarse", HttpStatus.FORBIDDEN);
        }

        cliente.get().setContrasena(clienteUpdateDTO.getContrasena());
        cliente.get().setEstado(clienteUpdateDTO.getEstado());
        cliente.get().setNombre(clienteUpdateDTO.getNombre());
        cliente.get().setGenero(clienteUpdateDTO.getGenero());
        cliente.get().setEdad(clienteUpdateDTO.getEdad());
        cliente.get().setDireccion(clienteUpdateDTO.getDireccion());
        cliente.get().setIdentificacion(clienteUpdateDTO.getIdentificacion());
        cliente.get().setTelefono(clienteUpdateDTO.getTelefono());
        return clienteRepository.save(cliente.get());

    }
    public String eliminarCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            clienteRepository.deleteById(id);
            return "Cliente eliminado con éxito.";
        } else {
            return "Cliente no encontrado.";
        }
    }
}
