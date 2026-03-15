package com.eventmanager.coreservice.adapter.outbound.mongo;

import com.eventmanager.coreservice.adapter.outbound.mongo.document.EventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<EventDocument, String> {
    boolean existsByName(String eventName);
}
