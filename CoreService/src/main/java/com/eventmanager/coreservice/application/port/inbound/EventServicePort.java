package com.eventmanager.coreservice.application.port.inbound;

import com.eventmanager.coreservice.domain.model.Event;

import java.util.List;

public interface EventServicePort {
    Event createEvent(Event event);

    Event findEventById(String eventId);

    List<Event> findAllEvents();

    Event updateAvailableTickets(String eventId, String ticketId, int quantity);

    void deleteEvent(String eventId);
}
