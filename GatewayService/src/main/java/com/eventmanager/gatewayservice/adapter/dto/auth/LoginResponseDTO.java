package com.eventmanager.gatewayservice.adapter.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("expires_in")
        Long expiresIn
) {}
