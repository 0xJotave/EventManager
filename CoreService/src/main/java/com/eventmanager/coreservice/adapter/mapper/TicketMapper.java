package com.eventmanager.coreservice.adapter.mapper;

import com.eventmanager.coreservice.adapter.dto.TicketDTO;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.TicketDocument;
import com.eventmanager.coreservice.domain.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toDomain(TicketDocument ticketDocument);

    TicketDocument toDocument(Ticket ticket);

    Ticket toDomain(TicketDTO ticketDTO);

    TicketDTO toDTO(Ticket ticket);
}
