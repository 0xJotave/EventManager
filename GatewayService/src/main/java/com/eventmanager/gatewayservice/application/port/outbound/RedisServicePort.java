package com.eventmanager.gatewayservice.application.port.outbound;

import reactor.core.publisher.Mono;

public interface RedisServicePort {
    <T> Mono<T> get(String key, Class<T> clazz);
    Mono<Void> save(String key, Object value, long ttlInMinutes);
    Mono<Void> evict(String key);
}
