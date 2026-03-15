package com.eventmanager.coreservice.adapter.outbound.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "events")
public class EventDocument {
    @Id
    private String eventId;
    private String name;
    private LocalDateTime date;
    private String location;
    private List<TicketDocument> ticketTypes;
}
