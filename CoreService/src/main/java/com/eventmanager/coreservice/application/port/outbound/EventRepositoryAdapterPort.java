package com.eventmanager.coreservice.application.port.outbound;

import com.eventmanager.coreservice.domain.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryAdapterPort {
    Event saveEvent(Event event);

    Optional<Event> findEventById(String eventId);

    List<Event> findAllEvents();

    boolean eventExistsByName(String eventName);

    void deleteEventById(String eventId);
}
