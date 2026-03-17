package com.eventmanager.gatewayservice.adapter.outbound.client;

import com.eventmanager.gatewayservice.adapter.dto.EventRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.EventResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.UpdateEventDTO;
import com.eventmanager.gatewayservice.application.port.outbound.EventClientPort;
import com.eventmanager.gatewayservice.application.port.outbound.RedisServicePort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class EventClient implements EventClientPort {

    private final WebClient webClient;
    private final RedisServicePort redisServicePort;

    public EventClient(WebClient.Builder builder, RedisServicePort redisServicePort) {
        this.webClient = builder.build();
        this.redisServicePort = redisServicePort;
    }

    @Override
    public Mono<EventResponseDTO> postEvent(EventRequestDTO eventDTO) {
        return webClient.post()
                .uri("/api/v1/events")
                .bodyValue(eventDTO)
                .retrieve()
                .bodyToMono(EventResponseDTO.class);
    }

    @Override
    public Mono<EventResponseDTO> findEventById(String eventId) {

        String cacheKey = "event:" + eventId;

        return redisServicePort.get(cacheKey, EventResponseDTO.class)
                .switchIfEmpty(
                        Mono.defer(() ->
                                        webClient.get()
                                                .uri("/api/v1/events/{eventId}", eventId)
                                                .retrieve()
                                                .bodyToMono(EventResponseDTO.class)
                                )
                                .timeout(Duration.ofSeconds(2))
                                .retry(2)
                                .flatMap(event ->
                                        redisServicePort.save(cacheKey, event, 10)
                                                .thenReturn(event)
                                )
                );
    }

    @Override
    public Flux<EventResponseDTO> findAllEvents() {
        return webClient.get()
                .uri("/api/v1/events")
                .retrieve()
                .bodyToFlux(EventResponseDTO.class);
    }

    @Override
    public Mono<EventResponseDTO> updateStock(String eventId, String ticketId, int quantity) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/events/{eventId}/tickets/{ticketId}")
                        .queryParam("quantity", quantity)
                        .build(eventId, ticketId))
                .retrieve()
                .bodyToMono(EventResponseDTO.class)
                .flatMap(response ->
                        redisServicePort.evict("event:" + eventId)
                                .thenReturn(response)
                );
    }

    @Override
    public Mono<EventResponseDTO> updateEventInfo(String eventId, UpdateEventDTO eventUpdatedDTO) {
        return webClient.patch()
                .uri("/api/v1/events/{eventId}", eventId)
                .bodyValue(eventUpdatedDTO)
                .retrieve()
                .bodyToMono(EventResponseDTO.class)
                .flatMap(response ->
                        redisServicePort.evict("event:" + eventId)
                                .thenReturn(response)
                );
    }

    @Override
    public Mono<Void> deleteEvent(String eventId) {
        return webClient.delete()
                .uri("/api/v1/events/{eventId}", eventId)
                .retrieve()
                .bodyToMono(Void.class)
                .then(redisServicePort.evict("event:" + eventId));
    }
}
