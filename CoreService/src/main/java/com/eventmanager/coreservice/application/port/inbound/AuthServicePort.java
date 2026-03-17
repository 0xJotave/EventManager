package com.eventmanager.coreservice.application.port.inbound;

import com.eventmanager.coreservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.coreservice.adapter.dto.UserRegistrationDTO;

import java.util.Map;

public interface AuthServicePort {
    void registerUser(UserRegistrationDTO dto);
    Map<String, Object> login(LoginRequestDTO dto);
}
