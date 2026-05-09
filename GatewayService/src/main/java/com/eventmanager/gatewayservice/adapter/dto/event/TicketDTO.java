package com.eventmanager.gatewayservice.adapter.dto.event;

import java.math.BigDecimal;

public record TicketDTO(
        String ticketId,
        String type,
        BigDecimal price,
        Integer totalQuantity,
        Integer availableQuantity,
        Integer currentBatch
) {
}
