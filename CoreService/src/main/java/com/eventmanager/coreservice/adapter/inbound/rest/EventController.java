package com.eventmanager.coreservice.adapter.inbound.rest;

import com.eventmanager.coreservice.adapter.dto.CreateEventDTO;
import com.eventmanager.coreservice.adapter.dto.ResponseEventDTO;
import com.eventmanager.coreservice.adapter.mapper.EventMapper;
import com.eventmanager.coreservice.application.port.inbound.EventServicePort;
import com.eventmanager.coreservice.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventServicePort eventServicePort;
    private final EventMapper eventMapper;

    @PostMapping()
    public ResponseEntity<ResponseEventDTO> createEvent(@RequestBody CreateEventDTO eventDTO) {
        Event event = eventMapper.toDomain(eventDTO);
        Event createdEvent = eventServicePort.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toDTO(createdEvent));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ResponseEventDTO> findEventById(@PathVariable String eventId) {
        Event event = eventServicePort.findEventById(eventId);
        return ResponseEntity.ok(eventMapper.toDTO(event));
    }

    @GetMapping
    public ResponseEntity<List<ResponseEventDTO>> findAllEvents() {
        return ResponseEntity.ok(eventServicePort.findAllEvents().stream().map(eventMapper::toDTO).toList());
    }

    @PatchMapping("/{eventId}/tickets/{ticketId}")
    public ResponseEntity<ResponseEventDTO> updateAvailableTickets(@PathVariable String eventId,
                                                                   @PathVariable String ticketId,
                                                                   @RequestParam int quantity) {
        Event updated = eventServicePort.updateAvailableTickets(eventId, ticketId, quantity);
        return ResponseEntity.ok(eventMapper.toDTO(updated));
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventById(@PathVariable String eventId) {
        eventServicePort.deleteEvent(eventId);
    }
}
