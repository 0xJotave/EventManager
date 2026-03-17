package com.eventmanager.coreservice.adapter.inbound.rest;

import com.eventmanager.coreservice.adapter.dto.ResponsePurchaseDTO;
import com.eventmanager.coreservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.coreservice.application.port.inbound.PurchaseServicePort;
import com.eventmanager.coreservice.domain.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseServicePort purchaseServicePort;
    private final PurchaseMapper purchaseMapper;

    @GetMapping("/{purchaseId}")
    public ResponseEntity<ResponsePurchaseDTO> findPurchaseById(@PathVariable String purchaseId) {
        Purchase purchase = purchaseServicePort.findPurchaseById(purchaseId);
        return ResponseEntity.ok(purchaseMapper.toDTO(purchase));
    }

    @DeleteMapping("/{purchaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelPurchase(@PathVariable String purchaseId) {
        purchaseServicePort.cancelPurchase(purchaseId);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> findAllPurchases() {
        return ResponseEntity.ok(purchaseServicePort.findAllPurchases().stream().map(purchaseMapper::toDTO).toList());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ResponsePurchaseDTO>> findPurchasesByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(purchaseServicePort.findPurchasesByEvent(eventId)
                .stream()
                .map(purchaseMapper::toDTO)
                .toList());
    }
}
