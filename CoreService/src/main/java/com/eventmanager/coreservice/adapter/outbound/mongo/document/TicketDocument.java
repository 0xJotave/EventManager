package com.eventmanager.coreservice.adapter.outbound.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDocument {
    private String ticketId;
    private String type;
    private BigDecimal price;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer currentBatch;
    private Integer soldQuantity;
    private BigDecimal basePrice;
}
