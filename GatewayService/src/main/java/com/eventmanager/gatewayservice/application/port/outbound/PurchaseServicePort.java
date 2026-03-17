package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseServicePort {
    Mono<PurchaseResponseDTO> execute(String eventId, String ticketId,
                                      PurchaseRequestDTO request, String username);
    Flux<PurchaseResponseDTO> getMyPurchases(String customerName);
}
