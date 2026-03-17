package com.eventmanager.coreservice.adapter.dto;

public record UserRegistrationDTO(
        String username,
        String email,
        String fullName,
        String password
) {}