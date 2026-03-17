package com.eventmanager.gatewayservice.adapter.inbound.rest;

import com.eventmanager.gatewayservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.UserRegistrationDTO;
import com.eventmanager.gatewayservice.adapter.outbound.client.AuthClient;
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
    public Mono<Void> signUp(@RequestBody UserRegistrationDTO dto) {
        return authClientPort.signUp(dto);
    }

    @PostMapping("/login")
    public Mono<Map> login(@RequestBody LoginRequestDTO dto) {
        return authClientPort.login(dto);
    }
}
