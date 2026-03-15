package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.application.port.inbound.EventServicePort;
import com.eventmanager.coreservice.application.port.outbound.EventRepositoryAdapterPort;
import com.eventmanager.coreservice.domain.exception.EventNotFound;
import com.eventmanager.coreservice.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements EventServicePort {

    private final EventRepositoryAdapterPort eventRepositoryAdapterPort;

    @Override
    public Event createEvent(Event event) {
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
    public Event updateAvailableTickets(String eventId, String ticketId, int quantity) {
        Event event = eventRepositoryAdapterPort.findEventById(eventId)
                .orElseThrow(() -> new EventNotFound("Event Not Found"));

        event.updateTicketQuantity(ticketId, quantity);
        return eventRepositoryAdapterPort.saveEvent(event);
    }

    @Override
    public void deleteEvent(String eventId) {
        eventRepositoryAdapterPort.deleteEventById(eventId);
    }
}
