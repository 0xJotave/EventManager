package com.eventmanager.coreservice.domain.model;

import java.math.BigDecimal;

public class Ticket {
    private String ticketId;
    private String type;
    private BigDecimal price;
    private Integer totalQuantity;
    private Integer availableQuantity;

    public Ticket(String ticketId, String type, BigDecimal price, Integer totalQuantity, Integer availableQuantity) {
        this.ticketId = ticketId;
        this.type = type;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public Ticket() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
