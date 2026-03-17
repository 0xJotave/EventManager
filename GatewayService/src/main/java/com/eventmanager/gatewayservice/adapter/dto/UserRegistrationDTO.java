package com.eventmanager.gatewayservice.adapter.dto;

public record UserRegistrationDTO(
        String username,
        String email,
        String fullName,
        String password
) {}
