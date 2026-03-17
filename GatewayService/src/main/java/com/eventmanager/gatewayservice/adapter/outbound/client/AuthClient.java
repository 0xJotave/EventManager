package com.eventmanager.gatewayservice.adapter.outbound.client;

import com.eventmanager.gatewayservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.UserRegistrationDTO;
import com.eventmanager.gatewayservice.application.port.outbound.AuthClientPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthClient implements AuthClientPort {
    private final WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8081").build();
    }

    public Mono<Void> signUp(UserRegistrationDTO dto) {
        return webClient.post()
                .uri("/api/v1/auth/signup")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Map> login(LoginRequestDTO dto) {
        return webClient.post()
                .uri("/api/v1/auth/login")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Map.class);
    }
}