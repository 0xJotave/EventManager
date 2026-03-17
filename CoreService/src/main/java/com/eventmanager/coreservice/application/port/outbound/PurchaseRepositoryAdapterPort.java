package com.eventmanager.coreservice.application.port.outbound;

import com.eventmanager.coreservice.domain.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepositoryAdapterPort {
    Purchase savePurchase(Purchase purchase);

    void deletePurchase(String purchaseId);

    Optional<Purchase> findPurchaseById(String purchaseId);

    List<Purchase> findAllPurchases();

    List<Purchase> findPurchasesByEvent(String eventId);

    List<Purchase> findPurchasesByCustomerName(String customerName);
}
