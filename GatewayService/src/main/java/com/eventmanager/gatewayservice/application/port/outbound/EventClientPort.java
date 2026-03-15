package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.EventRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.EventResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventClientPort {
    Mono<EventResponseDTO> postEvent(EventRequestDTO eventDTO);
    Mono<EventResponseDTO> findEventById(String eventId);
    Flux<EventResponseDTO> findAllEvents();
    Mono<EventResponseDTO> updateAvailableTickets(String eventId, String ticketId, int quantity);
    Mono<Void> deleteEvent(String eventId);
}
