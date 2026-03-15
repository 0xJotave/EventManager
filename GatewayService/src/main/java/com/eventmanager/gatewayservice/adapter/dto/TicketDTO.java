package com.eventmanager.gatewayservice.adapter.dto;

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
