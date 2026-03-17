package com.eventmanager.coreservice.domain.model;

import com.eventmanager.coreservice.domain.exception.TicketNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String eventId;
    private String name;
    private LocalDateTime date;
    private String location;
    private List<Ticket> ticketTypes;

    public Event(String eventId, String name, LocalDateTime date, String location, List<Ticket> ticketTypes) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.location = location;
        this.ticketTypes = ticketTypes;
    }

    public Event() {
    }

    public void processSale(String ticketId, int quantity) {
        Ticket ticket = findTicketById(ticketId);
        ticket.applySale(quantity);
    }

    public void processReturn(String ticketId, int quantity) {
        Ticket ticket = findTicketById(ticketId);
        ticket.returnTicket(quantity);
    }

    private Ticket findTicketById(String ticketId) {
        return this.ticketTypes.stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found: " + ticketId));
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Ticket> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<Ticket> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}
