package com.eventmanager.gatewayservice.adapter.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseDTO(
        String eventId,
        String name,
        LocalDateTime date,
        String location,
        List<TicketDTO> ticketTypes
) {
}
