package com.eventmanager.coreservice.domain.model;

import com.eventmanager.coreservice.domain.exception.InsufficientTicketsException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ticket {
    private String ticketId;
    private String type;
    private BigDecimal price;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer currentBatch;
    private Integer soldQuantity;
    private BigDecimal basePrice;

    public Ticket(String ticketId, String type, BigDecimal price, Integer totalQuantity, Integer availableQuantity,
                  Integer currentBatch, Integer soldQuantity, BigDecimal basePrice) {
        this.ticketId = ticketId;
        this.type = type;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.currentBatch = 1;
        this.soldQuantity = 0;
        this.basePrice = basePrice;
    }

    public Ticket() {
    }

    public void applySale(int quantity) {
        if (quantity > availableQuantity) {
            throw new InsufficientTicketsException("The quantity exceeds the total limit");
        }

        soldQuantity += quantity;
        availableQuantity -= quantity;

        double percentage = (soldQuantity * 100.0) / totalQuantity;
        int newBatch = 1;
        BigDecimal multiplier = BigDecimal.ONE;

        if (percentage > 60) {
            newBatch = 4;
            multiplier = new BigDecimal("1.60");
        } else if (percentage > 30) {
            newBatch = 3;
            multiplier = new BigDecimal("1.40");
        } else if (percentage > 10) {
            newBatch = 2;
            multiplier = new BigDecimal("1.20");
        }

        this.currentBatch = newBatch;
        this.price = this.basePrice.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }

    public void returnTicket(int quantity) {
        if (quantity > soldQuantity) {
            throw new InsufficientTicketsException("The quantity exceeds the total limit");
        }

        availableQuantity += quantity;
        soldQuantity -= quantity;
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

    public Integer getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(Integer currentBatch) {
        this.currentBatch = currentBatch;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
