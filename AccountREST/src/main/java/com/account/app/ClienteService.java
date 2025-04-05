package com.account.app;

import com.account.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {

    private final WebClient.Builder webClientBuilder;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public ClienteService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<ClienteDTO> obtenerInformacionCliente(Long clienteId) {
        System.out.println(customerServiceUrl);
        return webClientBuilder.baseUrl(customerServiceUrl)
                .build()
                .get()
                .uri("/{id}", clienteId)
                .retrieve()
                .bodyToMono(ClienteDTO.class);
    }
}
