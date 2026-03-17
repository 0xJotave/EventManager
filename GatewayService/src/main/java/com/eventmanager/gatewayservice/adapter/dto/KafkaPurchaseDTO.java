package com.eventmanager.gatewayservice.adapter.dto;

public record KafkaPurchaseDTO(
        String purchaseId,
        String customerName,
        String eventId,
        String ticketId,
        Integer quantity
) {
}