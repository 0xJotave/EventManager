package com.eventmanager.gatewayservice.adapter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record UpdateEventDTO(
        String name,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Future(message = "The date must be in the future.")
        LocalDateTime date,

        String location
) {
}
