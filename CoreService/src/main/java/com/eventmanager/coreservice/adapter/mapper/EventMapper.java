package com.eventmanager.coreservice.adapter.mapper;

import com.eventmanager.coreservice.adapter.dto.CreateEventDTO;
import com.eventmanager.coreservice.adapter.dto.ResponseEventDTO;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.EventDocument;
import com.eventmanager.coreservice.domain.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TicketMapper.class)
public interface EventMapper {
    Event toDomain(EventDocument eventDocument);

    EventDocument toDocument(Event event);

    Event toDomain(CreateEventDTO eventDTO);

    ResponseEventDTO toDTO(Event event);
}
