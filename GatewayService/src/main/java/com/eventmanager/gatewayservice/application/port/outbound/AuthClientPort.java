package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.UserRegistrationDTO;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AuthClientPort {
    Mono<Void> signUp(UserRegistrationDTO dto);
    Mono<Map> login(LoginRequestDTO dto);
}
