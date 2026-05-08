package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.event.EventRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.event.EventResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.event.UpdateEventDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventClientPort {
    Mono<EventResponseDTO> postEvent(EventRequestDTO eventDTO);

    Mono<EventResponseDTO> findEventById(String eventId);

    Flux<EventResponseDTO> findAllEvents();

    Mono<EventResponseDTO> updateStock(String eventId, String ticketId, int quantity);

    Mono<EventResponseDTO> updateEventInfo(String eventId, UpdateEventDTO eventUpdatedDTO);

    Mono<Void> deleteEvent(String eventId);
}
