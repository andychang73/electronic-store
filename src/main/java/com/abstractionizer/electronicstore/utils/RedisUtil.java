package com.abstractionizer.electronicstore.utils;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.storage.redis.Redis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.abstractionizer.electronicstore.errors.Error.INTERNAL_SERVER_ERROR;

@Slf4j
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

    public boolean deleteKey(@NonNull final String key){
        String value = redis.getRedisTemplate().remove(key);
        return Objects.nonNull(value);
    }

    public void deleteLock(@NonNull final String key, @Nullable final String uuid){
        if(Objects.nonNull(uuid)){
            String uuidInRedis = get(key, String.class);
            if(uuid.equals(uuidInRedis)){
                if(Boolean.FALSE == deleteKey(key)){
                    log.error("Failed to delete redis lock key: {}", key);
                }
            }else{
                log.error("Can not del redis lock key, because uuid did not match, may be timeout, key: {}  uuid: {} uuidInRedis: {}", key, uuid, uuidInRedis);
            }
        }
    }

    @SneakyThrows
    public String tryGetLock(@NonNull final String key){
        String uuid = UUID.randomUUID().toString();
        String result = redis.getRedisTemplate().putIfAbsent(key, objectMapper.writeValueAsString(uuid));
        return result == null ? uuid : null;
    }

    public boolean doWithRedisLock(@NonNull final String key, @NonNull final DoInRedisLock doInRedisLock){
        String uuid = null;

        try{
            uuid = tryGetLock(key);
            if(Objects.nonNull(uuid)){
                doInRedisLock.doSomeThing();
                return true;
            }
        }finally {
            deleteLock(key, uuid);
        }
        return false;
    }

    public void doWithRedisLockOrThrow(@NonNull final String key, @NonNull final DoInRedisLock doInRedisLock){
        if(Boolean.FALSE == doWithRedisLock(key, doInRedisLock)){
            throw new BusinessException(INTERNAL_SERVER_ERROR, "System is busy please try again later");
        }
    }

    public interface DoInRedisLock{
        void doSomeThing();
    }
}
