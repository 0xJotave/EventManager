package com.eventmanager.gatewayservice.adapter.inbound.rest;

import com.eventmanager.gatewayservice.adapter.dto.EventRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.EventResponseDTO;
import com.eventmanager.gatewayservice.adapter.dto.UpdateEventDTO;
import com.eventmanager.gatewayservice.application.port.outbound.EventClientPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gateway/events")
@RequiredArgsConstructor
public class GatewayEventController {

    private final EventClientPort eventClientPort;

    @PostMapping
    public Mono<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventDTO) {
        return eventClientPort.postEvent(eventDTO);
    }

    @GetMapping("/{eventId}")
    public Mono<EventResponseDTO> findEventById(@PathVariable String eventId) {
        return eventClientPort.findEventById(eventId);
    }

    @GetMapping
    public Flux<EventResponseDTO> findAllEvents() {
        return eventClientPort.findAllEvents();
    }

    @PatchMapping("/{eventId}/tickets/{ticketId}")
    public Mono<EventResponseDTO> updateStock(@PathVariable String eventId,
                                              @PathVariable String ticketId,
                                              @RequestParam int quantity) {
        return eventClientPort.updateStock(eventId, ticketId, quantity);
    }

    @PatchMapping("/{eventId}")
    public Mono<EventResponseDTO> updateEventInfo(@PathVariable String eventId,
                                                  @RequestBody UpdateEventDTO eventUpdatedDTO) {
        return eventClientPort.updateEventInfo(eventId, eventUpdatedDTO);
    }

    @DeleteMapping("/{eventId}")
    public Mono<Void> deleteEvent(@PathVariable String eventId) {
        return eventClientPort.deleteEvent(eventId);
    }
}
