package com.eventmanager.gatewayservice.adapter.outbound.client;

import com.eventmanager.gatewayservice.adapter.dto.auth.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.LoginResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.UserRegistrationDTO;
import com.eventmanager.gatewayservice.application.port.outbound.AuthClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthClient implements AuthClientPort {

    private final WebClient webClient;
    private final String coreUrl;

    public AuthClient(WebClient.Builder builder, @Value("${app.keycloak.core-url}") String coreUrl) {
        this.coreUrl = coreUrl;
        this.webClient = builder.baseUrl(coreUrl).build();
    }

    @Override
    public Mono<Void> signUp(UserRegistrationDTO dto) {
        return webClient.post()
                .uri("/api/v1/auth/signup")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<LoginResponseDTO> login(LoginRequestDTO dto) {
        return webClient.post()
                .uri("/api/v1/auth/login")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(LoginResponseDTO.class);
    }
}