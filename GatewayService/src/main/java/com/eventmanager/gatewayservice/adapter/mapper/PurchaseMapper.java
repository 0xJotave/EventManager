package com.eventmanager.gatewayservice.adapter.mapper;

import com.eventmanager.gatewayservice.adapter.dto.KafkaPurchaseDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseRequestDTO;
import com.eventmanager.gatewayservice.adapter.dto.PurchaseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    @Mapping(target = "purchaseId", source = "purchaseId")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "customerName", source = "request.customerName")
    @Mapping(target = "quantity", source = "request.quantity")
    KafkaPurchaseDTO toKafkaDTO(PurchaseRequestDTO request, String eventId, String ticketId, String purchaseId);

    PurchaseResponseDTO toResponseDTO(KafkaPurchaseDTO kafkaDTO);
}
