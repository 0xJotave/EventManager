package com.eventmanager.coreservice.adapter.dto;

import com.eventmanager.coreservice.domain.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseEventDTO(
        String eventId,
        String name,
        LocalDateTime date,
        String location,
        List<TicketDTO> ticketTypes
) {
}
