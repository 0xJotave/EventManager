package com.eventmanager.coreservice.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePurchaseDTO(
        @NotBlank(message = "The purchaseId is required for tracking")
        String purchaseId,

        @NotBlank(message = "Customer name cannot be empty")
        String customerName,

        @NotBlank(message = "Event ID is mandatory")
        String eventId,

        @NotBlank(message = "Ticket ID is mandatory")
        String ticketId,

        @NotNull(message = "Quantity must be informed")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity
) {
}