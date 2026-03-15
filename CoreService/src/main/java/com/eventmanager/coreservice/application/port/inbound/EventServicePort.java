package com.eventmanager.coreservice.application.port.inbound;

import com.eventmanager.coreservice.adapter.dto.UpdateEventDTO;
import com.eventmanager.coreservice.domain.model.Event;

import java.util.List;

public interface EventServicePort {
    Event createEvent(Event event);

    Event findEventById(String eventId);

    List<Event> findAllEvents();

    Event updateStock(String eventId, String ticketId, int quantity);

    Event updateEventInfo(String eventId, UpdateEventDTO eventUpdateDTO);

    void deleteEvent(String eventId);
}
