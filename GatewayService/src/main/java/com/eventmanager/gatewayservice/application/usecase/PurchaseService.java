package com.eventmanager.gatewayservice.application.usecase;

import com.eventmanager.gatewayservice.adapter.dto.KafkaPurchaseDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import com.eventmanager.gatewayservice.adapter.mapper.PurchaseMapper;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseClientPort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseMessagePort;
import com.eventmanager.gatewayservice.application.port.outbound.PurchaseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService implements PurchaseServicePort {
    private final PurchaseMessagePort purchaseMessagePort;
    private final PurchaseClientPort purchaseClientPort;
    private final PurchaseMapper purchaseMapper;

    public Mono<PurchaseResponseDTO> execute(String eventId, String ticketId,
                                             PurchaseRequestDTO request, String username) {
        return Mono.fromCallable(() -> {
            String purchaseId = UUID.randomUUID().toString();

            var kafkaDTO = new KafkaPurchaseDTO(
                    purchaseId,
                    username,
                    eventId,
                    ticketId,
                    request.quantity(),
                    "PENDING",
                    null,
                    LocalDateTime.now()
            );

            purchaseMessagePort.sendPurchaseRequest(kafkaDTO);

            var response = purchaseMapper.toResponseDTO(kafkaDTO);

            return new PurchaseResponseDTO(
                    response.purchaseId(),
                    response.customerName(),
                    response.eventId(),
                    response.ticketId(),
                    response.quantity(),
                    "PENDING",
                    null,
                    LocalDateTime.now()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<PurchaseResponseDTO> getMyPurchases(String customerName) {
        return purchaseClientPort.findPurchasesByCustomer(customerName);
    }
}
