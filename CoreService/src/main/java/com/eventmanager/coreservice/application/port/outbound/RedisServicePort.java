package com.eventmanager.coreservice.application.port.outbound;

import java.util.Optional;

public interface RedisServicePort {
    <T> Optional<T> get(String key, Class<T> clazz);
    void save(String key, Object value, long ttlInMinutes);
    void evict(String key);
}
