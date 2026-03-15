package com.eventmanager.coreservice.adapter.outbound.mongo.impl;

import com.eventmanager.coreservice.adapter.outbound.mongo.EventRepository;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.EventDocument;
import com.eventmanager.coreservice.adapter.mapper.EventMapper;
import com.eventmanager.coreservice.application.port.outbound.EventRepositoryAdapterPort;
import com.eventmanager.coreservice.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventRepositoryAdapter implements EventRepositoryAdapterPort {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event saveEvent(Event event) {
        EventDocument eventDocument = eventMapper.toDocument(event);
        EventDocument savedDocument = eventRepository.save(eventDocument);
        return eventMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Event> findEventById(String eventId) {
        return eventRepository.findById(eventId).map(eventMapper::toDomain);
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDomain).toList();
    }

    @Override
    public boolean eventExistsById(String eventId) {
        return eventRepository.existsById(eventId);
    }

    @Override
    public void deleteEventById(String eventId) {
        eventRepository.deleteById(eventId);
    }
}
