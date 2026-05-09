package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.auth.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.LoginResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.UserRegistrationDTO;
import reactor.core.publisher.Mono;

public interface AuthClientPort {
    Mono<Void> signUp(UserRegistrationDTO dto);

    Mono<LoginResponseDTO> login(LoginRequestDTO dto);
}
