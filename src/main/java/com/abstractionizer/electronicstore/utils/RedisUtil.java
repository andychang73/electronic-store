package com.abstractionizer.electronicstore.utils;

import com.abstractionizer.electronicstore.storage.redis.Redis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private final Redis redis;
    private final ObjectMapper objectMapper;

    public RedisUtil(Redis redis, ObjectMapper objectMapper) {
        this.redis = redis;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public <T> void set(@NonNull final String key, @NonNull final T value){
        redis.getRedisTemplate().put(key, objectMapper.writeValueAsString(value));
    }

    @SneakyThrows
    public <T> T get(@NonNull final String key, @NonNull final Class<T> clazz){
        String value = redis.getRedisTemplate().get(key);
        return value == null ? null :objectMapper.readValue(value, clazz);
    }

    public <T> boolean isKeyExists(@NonNull final String key, @NonNull final Class<T> clazz){
        return get(key, clazz) != null;
    }

    @SneakyThrows
    public <T> T remove(@NonNull final String key, @NonNull final Class<T> clazz){
        String value = redis.getRedisTemplate().remove(key);
        return value == null ? null : objectMapper.readValue(value, clazz);
    }
}
