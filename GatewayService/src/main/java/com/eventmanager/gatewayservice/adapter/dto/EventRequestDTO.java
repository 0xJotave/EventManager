package com.eventmanager.gatewayservice.adapter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record EventRequestDTO(
        @NotBlank(message = "The event name is required")
        String name,

        @NotNull(message = "The event date is required.")
        @Future(message = "The date must be in the future.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime date,

        @NotBlank(message = "Location is mandatory")
        String location,

        @NotEmpty(message = "The event must have at least one type of ticket")
        List<TicketDTO> ticketTypes
) {
}
