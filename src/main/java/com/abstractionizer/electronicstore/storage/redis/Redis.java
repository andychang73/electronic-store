package com.abstractionizer.electronicstore.storage.redis;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Redis {

    private final ConcurrentHashMap<String, String> redisTemplate;

    public Redis() {
        this.redisTemplate = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, String> getRedisTemplate(){
        return this.redisTemplate;
    }
}
