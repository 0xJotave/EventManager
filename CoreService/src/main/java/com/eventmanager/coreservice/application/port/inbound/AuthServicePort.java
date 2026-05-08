package com.eventmanager.coreservice.application.port.inbound;

import com.eventmanager.coreservice.adapter.dto.auth.LoginRequestDTO;
import com.eventmanager.coreservice.adapter.dto.auth.LoginResponseDTO;
import com.eventmanager.coreservice.adapter.dto.auth.UserRegistrationDTO;

import java.util.Map;

public interface AuthServicePort {
    void registerUser(UserRegistrationDTO dto);
    LoginResponseDTO login(LoginRequestDTO dto);
}
