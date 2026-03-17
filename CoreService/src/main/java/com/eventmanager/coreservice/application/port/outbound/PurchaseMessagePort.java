package com.eventmanager.coreservice.application.port.outbound;

import com.eventmanager.coreservice.domain.model.Purchase;

public interface PurchaseMessagePort {
    void sendPurchaseResult(Purchase purchase);
}
