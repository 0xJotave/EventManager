package com.eventmanager.coreservice.adapter.mapper;

import com.eventmanager.coreservice.adapter.dto.TicketDTO;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.TicketDocument;
import com.eventmanager.coreservice.domain.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "basePrice", source = "price")
    @Mapping(target = "availableQuantity", source = "totalQuantity")
    @Mapping(target = "currentBatch", constant = "1")
    @Mapping(target = "soldQuantity", constant = "0")
    Ticket toDomain(TicketDTO ticketDTO);

    TicketDocument toDocument(Ticket ticket);

    Ticket toDomain(TicketDocument ticketDocument);

    TicketDTO toDTO(Ticket ticket);
}
