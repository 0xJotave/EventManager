package com.eventmanager.gatewayservice.adapter.inbound.rest;

import com.eventmanager.gatewayservice.adapter.dto.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseClientPort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gateway/purchases")
@RequiredArgsConstructor
public class GatewayPurchaseController {
    private final PurchaseClientPort purchaseClientPort;
    private final PurchaseServicePort purchaseServicePort;

    @PostMapping("/{eventId}/tickets/{ticketId}")
    public Mono<PurchaseResponseDTO> createPurchase(
            @PathVariable String eventId,
            @PathVariable String ticketId,
            @Valid @RequestBody PurchaseRequestDTO purchaseDTO) {

        return purchaseServicePort.execute(eventId, ticketId, purchaseDTO);
    }

    @GetMapping("/{purchaseId}")
    public Mono<PurchaseResponseDTO> findById(@PathVariable String purchaseId) {
        return purchaseClientPort.findPurchaseById(purchaseId);
    }

    @GetMapping
    public Flux<PurchaseResponseDTO> findAllPurchases() {
        return purchaseClientPort.findAllPurchases();
    }

    @GetMapping("/event/{eventId}")
    public Flux<PurchaseResponseDTO> findByEvent(@PathVariable String eventId) {
        return purchaseClientPort.findPurchasesByEvent(eventId);
    }

    @DeleteMapping("/{purchaseId}")
    public Mono<Void> cancelPurchase(@PathVariable String purchaseId) {
        return purchaseClientPort.cancelPurchase(purchaseId);
    }
}
