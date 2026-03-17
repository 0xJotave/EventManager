package com.eventmanager.coreservice.adapter.outbound.mongo.impl;

import com.eventmanager.coreservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.coreservice.adapter.outbound.mongo.PurchaseRepository;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.PurchaseDocument;
import com.eventmanager.coreservice.application.port.outbound.PurchaseRepositoryAdapterPort;
import com.eventmanager.coreservice.domain.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PurchaseRepositoryAdapter implements PurchaseRepositoryAdapterPort {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public Purchase savePurchase(Purchase purchase) {
        PurchaseDocument purchaseDocument = purchaseMapper.toDocument(purchase);
        return purchaseMapper.toDomain(purchaseRepository.save(purchaseDocument));
    }

    @Override
    public void deletePurchase(String purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    @Override
    public Optional<Purchase> findPurchaseById(String purchaseId) {
        return purchaseRepository.findById(purchaseId).map(purchaseMapper::toDomain);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll().stream().map(purchaseMapper::toDomain).toList();
    }

    @Override
    public List<Purchase> findPurchasesByEvent(String eventId) {
        return purchaseRepository.findPurchaseDocumentsByEventId(eventId).stream()
                .map(purchaseMapper::toDomain)
                .toList();
    }
}
