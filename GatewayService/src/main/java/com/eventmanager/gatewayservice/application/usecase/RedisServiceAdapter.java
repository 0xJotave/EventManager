package com.eventmanager.gatewayservice.application.usecase;

import com.eventmanager.gatewayservice.application.port.outbound.RedisServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceAdapter implements RedisServicePort {
    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public <T> Mono<T> get(String key, Class<T> clazz) {
        return redisTemplate.opsForValue().get(key).cast(clazz);
    }

    public Mono<Void> save(String key, Object value, long ttlInMinutes) {
        return redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(ttlInMinutes)).then();
    }

    public Mono<Void> evict(String key) {
        return redisTemplate.delete(key).then();
    }
}
