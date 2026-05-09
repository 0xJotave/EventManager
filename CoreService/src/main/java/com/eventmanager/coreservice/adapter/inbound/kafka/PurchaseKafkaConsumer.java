package com.eventmanager.coreservice.adapter.inbound.kafka;

import com.eventmanager.coreservice.adapter.dto.purchase.KafkaPurchaseDTO;
import com.eventmanager.coreservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.coreservice.application.port.inbound.PurchaseServicePort;
import com.eventmanager.coreservice.domain.model.Purchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseKafkaConsumer {

    private final PurchaseServicePort purchaseServicePort;
    private final PurchaseMapper purchaseMapper;

    @KafkaListener(
            topics = "purchase-requests",
            groupId = "core-service-group"
    )
    public void consume(KafkaPurchaseDTO purchaseDTO, Acknowledgment ack) {

        log.info("📥 Received purchase: {}", purchaseDTO.purchaseId());

        try {
            Purchase purchase = purchaseMapper.toDomain(purchaseDTO);

            purchaseServicePort.processPurchase(purchase);

            ack.acknowledge();

            log.info("✅ Purchase processed: {}", purchaseDTO.purchaseId());

        } catch (Exception e) {
            log.error("❌ Error processing purchase {}", purchaseDTO.purchaseId(), e);
            throw e;
        }
    }
}