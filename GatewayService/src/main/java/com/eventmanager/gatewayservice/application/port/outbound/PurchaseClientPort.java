package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseClientPort {
    Mono<PurchaseResponseDTO> findPurchaseById(String purchaseId);

    Flux<PurchaseResponseDTO> findAllPurchases();

    Flux<PurchaseResponseDTO> findPurchasesByEvent(String eventId);

    Mono<Void> cancelPurchase(String purchaseId);

    Flux<PurchaseResponseDTO> findPurchasesByCustomer(String customerName);
}
