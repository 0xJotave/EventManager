package com.eventmanager.coreservice.adapter.dto;

public record KafkaPurchaseDTO(
        String purchaseId,
        String customerName,
        String eventId,
        String ticketId,
        Integer quantity
) {
}
