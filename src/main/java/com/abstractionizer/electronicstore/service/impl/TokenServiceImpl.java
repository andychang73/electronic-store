package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.login.AccountInfo;
import com.abstractionizer.electronicstore.service.TokenService;
import com.abstractionizer.electronicstore.utils.RedisUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.abstractionizer.electronicstore.constant.RedisKey.getRKeyAdminToken;
import static com.abstractionizer.electronicstore.errors.Error.AUTH_ERROR;

@Service
public class TokenServiceImpl implements TokenService {

    private final RedisUtil redisUtil;

    public TokenServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean isTokenExists(@NonNull final String token) {
        return redisUtil.isKeyExists(token, String.class);
    }

    @Override
    public void associateTokenAndAccount(@NonNull final String key, @NonNull final AccountInfo accountInfo) {
        redisUtil.set(getRKeyAdminToken(key),accountInfo);
    }

    @Override
    public AccountInfo getAccountInfoByTokenOrThrow(@NonNull final String token) {
        return Optional.ofNullable(redisUtil.get(getRKeyAdminToken(token), AccountInfo.class))
                .orElseThrow(() -> new BusinessException(AUTH_ERROR));
    }
}
