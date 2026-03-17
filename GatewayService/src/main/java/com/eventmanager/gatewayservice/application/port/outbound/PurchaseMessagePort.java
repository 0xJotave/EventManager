package com.eventmanager.gatewayservice.application.port.outbound;

import com.eventmanager.gatewayservice.adapter.dto.KafkaPurchaseDTO;

public interface PurchaseMessagePort {
    void sendPurchaseRequest(KafkaPurchaseDTO kafkaDTO);
}
