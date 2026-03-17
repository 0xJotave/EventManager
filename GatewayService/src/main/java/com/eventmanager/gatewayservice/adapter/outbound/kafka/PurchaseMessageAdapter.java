package com.eventmanager.gatewayservice.adapter.outbound.kafka;

import com.eventmanager.gatewayservice.adapter.dto.KafkaPurchaseDTO;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseMessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseMessageAdapter implements PurchaseMessagePort {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendPurchaseRequest(KafkaPurchaseDTO kafkaDTO) {
        kafkaTemplate.send("purchase-requests", kafkaDTO.purchaseId(), kafkaDTO);
    }
}
