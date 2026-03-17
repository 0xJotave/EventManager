package com.eventmanager.coreservice.domain.model;

import com.eventmanager.coreservice.domain.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Purchase {
    private String purchaseId;
    private String customerName;
    private String eventId;
    private String ticketId;
    private Integer quantity;
    private Status status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    public Purchase(String purchaseId, String customerName, String eventId, String ticketId, Integer quantity,
                    BigDecimal totalAmount, Status status, LocalDateTime createdAt) {
        this.purchaseId = purchaseId;
        this.customerName = customerName;
        this.eventId = eventId;
        this.ticketId = ticketId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Purchase() {
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
