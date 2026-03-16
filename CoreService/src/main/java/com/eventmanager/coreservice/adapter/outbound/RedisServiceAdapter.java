package com.eventmanager.coreservice.adapter.outbound;

import com.eventmanager.coreservice.application.port.outbound.RedisServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisServiceAdapter implements RedisServicePort {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(clazz.cast(value));
    }

    @Override
    public void save(String key, Object value, long ttlInMinutes) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(ttlInMinutes));
    }

    @Override
    public void evict(String key) {
        redisTemplate.delete(key);
    }
}
