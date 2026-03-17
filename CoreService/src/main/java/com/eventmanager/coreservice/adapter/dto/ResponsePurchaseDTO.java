package com.eventmanager.coreservice.adapter.dto;

import com.eventmanager.coreservice.domain.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponsePurchaseDTO(
        String purchaseId,
        String customerName,
        String eventId,
        String ticketId,
        Integer quantity,
        Status status,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
