package com.eventmanager.gatewayservice.adapter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponseDTO(
        String purchaseId,
        String customerName,
        String eventId,
        String ticketId,
        Integer quantity,
        String status,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
