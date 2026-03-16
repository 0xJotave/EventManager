package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.adapter.dto.UpdateEventDTO;
import com.eventmanager.coreservice.application.port.inbound.EventServicePort;
import com.eventmanager.coreservice.application.port.outbound.EventRepositoryAdapterPort;
import com.eventmanager.coreservice.application.port.outbound.RedisServicePort;
import com.eventmanager.coreservice.domain.exception.EventAlreadyExists;
import com.eventmanager.coreservice.domain.exception.EventNotFound;
import com.eventmanager.coreservice.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements EventServicePort {

    private final EventRepositoryAdapterPort eventRepositoryAdapterPort;
    private final RedisServicePort redisServicePort;

    @Override
    public Event createEvent(Event event) {
        if (eventRepositoryAdapterPort.eventExistsByName(event.getName())) {
            throw new EventAlreadyExists("There is already an event with that name.");
        }
        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public Event findEventById(String eventId) {
        String cacheKey = "core:event:" + eventId;
        return redisServicePort.get(cacheKey, Event.class)
                .orElseGet(() -> {
                    Event event = eventRepositoryAdapterPort.findEventById(eventId)
                            .orElseThrow(() -> new EventNotFound("Event Not Found"));
                    redisServicePort.save(cacheKey, event, 10);

                    return event;
                });
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepositoryAdapterPort.findAllEvents();
    }

    @Override
    public Event updateStock(String eventId, String ticketId, int quantity) {
        Event event = findEventById(eventId);

        if (quantity > 0) {
            event.processSale(ticketId, quantity);
        } else {
            event.processReturn(ticketId, Math.abs(quantity));
        }

        redisServicePort.evict("core:event:" + eventId);
        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public Event updateEventInfo(String eventId, UpdateEventDTO eventUpdateDTO) {
        Event event = findEventById(eventId);

        if (eventUpdateDTO.name() != null) event.setName(eventUpdateDTO.name());
        if (eventUpdateDTO.date() != null) event.setDate(eventUpdateDTO.date());
        if (eventUpdateDTO.location() != null) event.setLocation(eventUpdateDTO.location());

        redisServicePort.evict("core:event:" + eventId);
        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public void deleteEvent(String eventId) {
        eventRepositoryAdapterPort.deleteEventById(eventId);
        redisServicePort.evict("core:event:" + eventId);
    }
}
