package com.eventmanager.gatewayservice.adapter.dto.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequestDTO(
        @NotNull
        @Positive
        Integer quantity
){
}
