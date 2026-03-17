package com.eventmanager.gatewayservice.adapter.outbound.client;

import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseClientPort;
import com.eventmanager.gatewayservice.application.port.outbound.RedisServicePort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PurchaseClient implements PurchaseClientPort {
    private final WebClient webClient;
    private final RedisServicePort redisServicePort;

    public PurchaseClient(WebClient.Builder builder, RedisServicePort redisServicePort) {
        this.webClient = builder.build();
        this.redisServicePort = redisServicePort;
    }

    @Override
    public Mono<PurchaseResponseDTO> findPurchaseById(String purchaseId) {
        String cacheKey = "gateway:purchase:" + purchaseId;

        return redisServicePort.get(cacheKey, PurchaseResponseDTO.class)
                .switchIfEmpty(
                        webClient.get()
                                .uri("/api/v1/purchases/{purchaseId}", purchaseId)
                                .retrieve()
                                .bodyToMono(PurchaseResponseDTO.class)
                                .flatMap(purchase ->
                                        redisServicePort.save(cacheKey, purchase, 2)
                                                .thenReturn(purchase)
                                )
                );
    }

    @Override
    public Flux<PurchaseResponseDTO> findAllPurchases() {
        return webClient.get()
                .uri("/api/v1/purchases")
                .retrieve()
                .bodyToFlux(PurchaseResponseDTO.class);
    }

    @Override
    public Flux<PurchaseResponseDTO> findPurchasesByEvent(String eventId) {
        return webClient.get()
                .uri("/api/v1/purchases/event/{eventId}", eventId)
                .retrieve()
                .bodyToFlux(PurchaseResponseDTO.class);
    }

    @Override
    public Mono<Void> cancelPurchase(String purchaseId) {
        return webClient.delete()
                .uri("/api/v1/purchases/{purchaseId}", purchaseId)
                .retrieve()
                .bodyToMono(Void.class)
                .then(redisServicePort.evict("purchase:" + purchaseId));
    }
}
