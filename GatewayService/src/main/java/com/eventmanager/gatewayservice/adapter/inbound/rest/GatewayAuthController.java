package com.eventmanager.gatewayservice.adapter.inbound.rest;

import com.eventmanager.gatewayservice.adapter.dto.auth.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.LoginResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.auth.UserRegistrationDTO;
import com.eventmanager.gatewayservice.application.port.outbound.AuthClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/gateway/auth")
@RequiredArgsConstructor
public class GatewayAuthController {

    private final AuthClientPort authClientPort;

    @PostMapping("/signup")
    public Mono<Void> signUp(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return authClientPort.signUp(userRegistrationDTO);
    }

    @PostMapping("/login")
    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO userRegistrationDTO) {
        return authClientPort.login(userRegistrationDTO);
    }
}
