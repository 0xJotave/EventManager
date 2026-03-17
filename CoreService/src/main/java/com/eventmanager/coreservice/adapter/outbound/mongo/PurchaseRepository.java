package com.eventmanager.coreservice.adapter.outbound.mongo;

import com.eventmanager.coreservice.adapter.outbound.mongo.document.PurchaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends MongoRepository<PurchaseDocument, String> {
    List<PurchaseDocument> findPurchaseDocumentsByEventId(String eventId);
}
