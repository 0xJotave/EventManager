package com.eventmanager.coreservice.application.port.inbound;

import com.eventmanager.coreservice.domain.model.Purchase;

import java.util.List;

public interface PurchaseServicePort {
    Purchase processPurchase(Purchase purchase);

    void cancelPurchase(String purchaseId);

    Purchase findPurchaseById(String purchaseId);

    List<Purchase> findAllPurchases();

    List<Purchase> findPurchasesByEvent(String eventId);

    List<Purchase> findPurchasesByCustomer(String customerName);
}
