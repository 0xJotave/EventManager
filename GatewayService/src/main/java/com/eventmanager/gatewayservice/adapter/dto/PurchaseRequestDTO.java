package com.eventmanager.gatewayservice.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequestDTO(
        @NotBlank(message = "Customer name cannot be empty")
        String customerName,

        @NotNull(message = "Quantity must be informed")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity
) {
}
