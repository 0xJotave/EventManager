package com.eventmanager.coreservice.adapter.inbound.rest;

import com.eventmanager.coreservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.coreservice.adapter.dto.UserRegistrationDTO;
import com.eventmanager.coreservice.application.port.inbound.AuthServicePort;
import com.eventmanager.coreservice.application.usecase.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServicePort authServicePort;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserRegistrationDTO dto) {
        authServicePort.registerUser(dto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authServicePort.login(dto));
    }
}
