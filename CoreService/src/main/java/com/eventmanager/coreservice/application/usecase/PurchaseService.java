package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.application.port.inbound.PurchaseServicePort;
import com.eventmanager.coreservice.application.port.outbound.EventRepositoryAdapterPort;
import com.eventmanager.coreservice.application.port.outbound.PurchaseMessagePort;
import com.eventmanager.coreservice.application.port.outbound.PurchaseRepositoryAdapterPort;
import com.eventmanager.coreservice.application.port.outbound.RedisServicePort;
import com.eventmanager.coreservice.domain.enums.Status;
import com.eventmanager.coreservice.domain.exception.EventNotFoundException;
import com.eventmanager.coreservice.domain.exception.InsufficientTicketsException;
import com.eventmanager.coreservice.domain.exception.PurchaseNotFoundException;
import com.eventmanager.coreservice.domain.exception.TicketNotFoundException;
import com.eventmanager.coreservice.domain.model.Event;
import com.eventmanager.coreservice.domain.model.Purchase;
import com.eventmanager.coreservice.domain.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseService implements PurchaseServicePort {
    private final PurchaseRepositoryAdapterPort purchaseRepositoryAdapterPort;
    private final EventRepositoryAdapterPort eventRepositoryAdapterPort;
    private final PurchaseMessagePort purchaseMessagePort;
    private final RedisServicePort redisServicePort;

    @Override
    public Purchase processPurchase(Purchase purchase) {

        var existingPurchase = purchaseRepositoryAdapterPort
                .findPurchaseById(purchase.getPurchaseId());

        if (existingPurchase.isPresent()) {
            return existingPurchase.get();
        }

        Event event = eventRepositoryAdapterPort.findEventById(purchase.getEventId())
                .orElseThrow(() -> {
                    log.error("Event not found: {}", purchase.getEventId());
                    return new EventNotFoundException("Event Not Found: " + purchase.getEventId());
                });

        try {
            event.processSale(purchase.getTicketId(), purchase.getQuantity());

            Ticket ticket = event.getTicketTypes().stream()
                    .filter(t -> t.getTicketId().equals(purchase.getTicketId()))
                    .findFirst()
                    .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found"));

            purchase.setStatus(Status.APPROVED);
            purchase.setTotalAmount(
                    ticket.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()))
            );
            purchase.setCreatedAt(LocalDateTime.now());

            eventRepositoryAdapterPort.saveEvent(event);
            redisServicePort.evict("core:event:" + event.getEventId());

        } catch (InsufficientTicketsException | TicketNotFoundException e) {

            log.warn("Purchase rejected: {}", e.getMessage());

            purchase.setStatus(Status.REJECTED);
            purchase.setTotalAmount(BigDecimal.ZERO);
        }

        Purchase savedPurchase = purchaseRepositoryAdapterPort.savePurchase(purchase);

        purchaseMessagePort.sendPurchaseResult(savedPurchase);

        redisServicePort.evict("core:purchase:" + purchase.getPurchaseId());

        return savedPurchase;
    }

    @Override
    public void cancelPurchase(String purchaseId) {
        Purchase purchase = purchaseRepositoryAdapterPort.findPurchaseById(purchaseId)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase Not Found: " + purchaseId));

        if (purchase.getStatus() == Status.CANCELLED) {
            return;
        }

        purchase.setStatus(Status.CANCELLED);
        purchaseRepositoryAdapterPort.savePurchase(purchase);

        eventRepositoryAdapterPort.findEventById(purchase.getEventId()).ifPresent(event -> {
            event.processReturn(purchase.getTicketId(), purchase.getQuantity());
            eventRepositoryAdapterPort.saveEvent(event);
            redisServicePort.evict("core:event:" + event.getEventId());
        });

        redisServicePort.evict("core:purchase:" + purchaseId);
    }

    @Override
    public Purchase findPurchaseById(String purchaseId) {
        String cacheKey = "core:purchase:" + purchaseId;

        return redisServicePort.get(cacheKey, Purchase.class)
                .orElseGet(() -> {
                    Purchase purchase = purchaseRepositoryAdapterPort.findPurchaseById(purchaseId)
                            .orElseThrow(() -> new PurchaseNotFoundException("Purchase Not Found"));

                    redisServicePort.save(cacheKey, purchase, 5);
                    return purchase;
                });
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepositoryAdapterPort.findAllPurchases();
    }

    @Override
    public List<Purchase> findPurchasesByEvent(String eventId) {
        return purchaseRepositoryAdapterPort.findPurchasesByEvent(eventId);
    }

    @Override
    public List<Purchase> findPurchasesByCustomer(String customerName) {
        return purchaseRepositoryAdapterPort.findPurchasesByCustomerName(customerName);
    }
}
