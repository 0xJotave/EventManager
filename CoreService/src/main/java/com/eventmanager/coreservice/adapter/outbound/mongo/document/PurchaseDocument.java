package com.eventmanager.coreservice.adapter.outbound.mongo.document;

import com.eventmanager.coreservice.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "purchases")
public class PurchaseDocument {
    @Id
    private String purchaseId;
    private String customerName;
    private String eventId;
    private String ticketId;
    private Integer quantity;
    private Status status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
