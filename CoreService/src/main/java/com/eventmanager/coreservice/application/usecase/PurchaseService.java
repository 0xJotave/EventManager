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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService implements PurchaseServicePort {
    private final PurchaseRepositoryAdapterPort purchaseRepositoryAdapterPort;
    private final EventRepositoryAdapterPort eventRepositoryAdapterPort;
    private final PurchaseMessagePort purchaseMessagePort;
    private final RedisServicePort redisServicePort;

    @Override
    public Purchase processPurchase(Purchase purchase) {
        Event event = eventRepositoryAdapterPort.findEventById(purchase.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event Not Found: " + purchase.getEventId()));
        try {
            event.processSale(purchase.getTicketId(), purchase.getQuantity());

            Ticket ticket = event.getTicketTypes().stream()
                    .filter(t -> t.getTicketId().equals(purchase.getTicketId()))
                    .findFirst()
                    .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found"));

            purchase.setStatus(Status.APPROVED);
            purchase.setTotalAmount(ticket.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
            purchase.setCreatedAt(LocalDateTime.now());
            eventRepositoryAdapterPort.saveEvent(event);
        } catch (InsufficientTicketsException | TicketNotFoundException e) {
            purchase.setStatus(Status.REJECTED);
            purchase.setTotalAmount(BigDecimal.ZERO);
        }
        Purchase savedPurchase = purchaseRepositoryAdapterPort.savePurchase(purchase);
        purchaseMessagePort.sendPurchaseResult(purchase);
        redisServicePort.evict("core:purchase:" + purchase.getPurchaseId());

        return savedPurchase;
    }

    @Override
    public void cancelPurchase(String purchaseId) {
        purchaseRepositoryAdapterPort.deletePurchase(purchaseId);
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
