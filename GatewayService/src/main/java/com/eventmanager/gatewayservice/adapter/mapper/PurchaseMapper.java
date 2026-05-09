package com.eventmanager.gatewayservice.adapter.mapper;

import com.eventmanager.gatewayservice.adapter.dto.purchase.KafkaPurchaseDTO;
import com.eventmanager.gatewayservice.adapter.dto.purchase.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.purchase.PurchaseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    @Mapping(target = "purchaseId", source = "purchaseId")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "quantity", source = "request.quantity")
    @Mapping(target = "customerName", source = "username")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    KafkaPurchaseDTO toKafkaDTO(PurchaseRequestDTO request, String eventId, String ticketId, String purchaseId,
                                String username);

    PurchaseResponseDTO toResponseDTO(KafkaPurchaseDTO kafkaDTO);
}
