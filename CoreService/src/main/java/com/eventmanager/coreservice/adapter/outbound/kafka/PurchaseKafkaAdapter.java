package com.eventmanager.coreservice.adapter.outbound.kafka;

import com.eventmanager.coreservice.application.port.outbound.PurchaseMessagePort;
import com.eventmanager.coreservice.domain.enums.Status;
import com.eventmanager.coreservice.domain.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseKafkaAdapter implements PurchaseMessagePort {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendPurchaseResult(Purchase purchase) {
        kafkaTemplate.send("purchase-results", purchase.getPurchaseId(), purchase);
    }
}
