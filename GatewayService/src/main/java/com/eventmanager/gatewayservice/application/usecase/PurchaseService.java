package com.eventmanager.gatewayservice.application.usecase;

import com.eventmanager.gatewayservice.adapter.dto.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import com.eventmanager.gatewayservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseMessagePort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService implements PurchaseServicePort {
    private final PurchaseMessagePort purchaseMessagePort;
    private final PurchaseMapper purchaseMapper;

    public Mono<PurchaseResponseDTO> execute(String eventId, String ticketId, PurchaseRequestDTO request) {
        return Mono.fromCallable(() -> {
            String purchaseId = UUID.randomUUID().toString();

            var kafkaDTO = purchaseMapper.toKafkaDTO(request, eventId, ticketId, purchaseId);

            purchaseMessagePort.sendPurchaseRequest(kafkaDTO);

            return purchaseMapper.toResponseDTO(kafkaDTO);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
