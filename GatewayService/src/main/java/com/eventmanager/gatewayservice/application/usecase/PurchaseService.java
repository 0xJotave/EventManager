package com.eventmanager.gatewayservice.application.usecase;

import com.eventmanager.gatewayservice.adapter.dto.purchase.KafkaPurchaseDTO;
import com.eventmanager.gatewayservice.adapter.dto.purchase.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.purchase.PurchaseResponseDTO;
import com.eventmanager.gatewayservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseClientPort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseMessagePort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService implements PurchaseServicePort {

    private final PurchaseMessagePort purchaseMessagePort;
    private final PurchaseClientPort purchaseClientPort;
    private final PurchaseMapper purchaseMapper;

    @Override
    public Mono<PurchaseResponseDTO> execute(String eventId, String ticketId,
                                             PurchaseRequestDTO request, String username) {

        String purchaseId = UUID.randomUUID().toString();

        KafkaPurchaseDTO kafkaDTO = purchaseMapper.toKafkaDTO(
                request, eventId, ticketId, purchaseId, username
        );

        return Mono.fromRunnable(() ->
                purchaseMessagePort.sendPurchaseRequest(kafkaDTO)
        ).thenReturn(purchaseMapper.toResponseDTO(kafkaDTO));
    }

    @Override
    public Flux<PurchaseResponseDTO> getMyPurchases(String customerName) {
        return purchaseClientPort.findPurchasesByCustomer(customerName);
    }
}