package com.eventmanager.coreservice.adapter.dto;

import java.math.BigDecimal;

public record TicketDTO(
        String ticketId,
        String type,
        BigDecimal price,
        Integer totalQuantity,
        Integer availableQuantity
) {
}
