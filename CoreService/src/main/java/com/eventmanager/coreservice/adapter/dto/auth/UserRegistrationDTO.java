package com.eventmanager.coreservice.adapter.dto.auth;

public record UserRegistrationDTO(
        String username,
        String email,
        String fullName,
        String password
) {}