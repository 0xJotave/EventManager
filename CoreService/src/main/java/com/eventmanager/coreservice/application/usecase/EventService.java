package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.adapter.dto.UpdateEventDTO;
import com.eventmanager.coreservice.application.port.inbound.EventServicePort;
import com.eventmanager.coreservice.application.port.outbound.EventRepositoryAdapterPort;
import com.eventmanager.coreservice.domain.exception.EventAlreadyExists;
import com.eventmanager.coreservice.domain.exception.EventNotFound;
import com.eventmanager.coreservice.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService implements EventServicePort {

    private final EventRepositoryAdapterPort eventRepositoryAdapterPort;

    @Override
    public Event createEvent(Event event) {
        if (eventRepositoryAdapterPort.eventExistsByName(event.getName())) {
            throw new EventAlreadyExists("There is already an event with that name.");
        }
        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public Event findEventById(String eventId) {
        return eventRepositoryAdapterPort.findEventById(eventId)
                .orElseThrow(() -> new EventNotFound("Event Not Found"));
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

        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public Event updateEventInfo(String eventId, UpdateEventDTO eventUpdateDTO) {
        Event event = findEventById(eventId);

        if (eventUpdateDTO.name() != null) event.setName(eventUpdateDTO.name());
        if (eventUpdateDTO.date() != null) event.setDate(eventUpdateDTO.date());
        if (eventUpdateDTO.location() != null) event.setLocation(eventUpdateDTO.location());

        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public void deleteEvent(String eventId) {
        eventRepositoryAdapterPort.deleteEventById(eventId);
    }
}
