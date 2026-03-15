package com.eventmanager.gatewayservice.adapter.outbound.client;

import com.eventmanager.gatewayservice.adapter.dto.EventRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.EventResponseDTO;
import com.eventmanager.gatewayservice.application.port.outbound.EventClientPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EventClient implements EventClientPort {

    private final WebClient webClient;

    public EventClient(WebClient.Builder builder) {
        this.webClient = builder.build();
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
        return webClient.get()
                .uri("/api/v1/events/{eventId}", eventId)
                .retrieve()
                .bodyToMono(EventResponseDTO.class);
    }

    @Override
    public Flux<EventResponseDTO> findAllEvents() {
        return webClient.get()
                .uri("/api/v1/events")
                .retrieve()
                .bodyToFlux(EventResponseDTO.class);
    }

    @Override
    public Mono<EventResponseDTO> updateAvailableTickets(String eventId, String ticketId, int quantity) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/events/{eventId}/tickets/{ticketId}")
                        .queryParam("quantity", quantity)
                        .build(eventId, ticketId))
                .retrieve()
                .bodyToMono(EventResponseDTO.class);
    }

    @Override
    public Mono<Void> deleteEvent(String eventId) {
        return webClient.delete()
                .uri("/api/v1/events/{eventId}", eventId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
