package com.eventmanager.coreservice.adapter.mapper;

import com.eventmanager.coreservice.adapter.dto.KafkaPurchaseDTO;
import com.eventmanager.coreservice.adapter.dto.ResponsePurchaseDTO;
import com.eventmanager.coreservice.adapter.outbound.mongo.document.PurchaseDocument;
import com.eventmanager.coreservice.domain.model.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    Purchase toDomain(PurchaseDocument purchaseDocument);

    PurchaseDocument toDocument(Purchase purchase);

    Purchase toDomain(KafkaPurchaseDTO purchaseDTO);

    ResponsePurchaseDTO toDTO(Purchase purchase);
}
