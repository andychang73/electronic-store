package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.TokenBusiness;
import com.abstractionizer.electronicstore.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenBusinessImpl implements TokenBusiness {

    private final Integer tokenGenerateLimit;
    private final TokenService tokenService;


    public TokenBusinessImpl(@Value("${token.generate.limit}") Integer tokenGenerateLimit,
                             TokenService tokenService) {
        this.tokenGenerateLimit = tokenGenerateLimit;
        this.tokenService = tokenService;
    }

    @Override
    public String checkAndGenerateToken() {
        String token;

        int count = 0;
        while(true){
            token = UUID.randomUUID().toString();
            if(Boolean.FALSE == tokenService.isTokenExists(token)){
                break;
            }
            if(++count == tokenGenerateLimit){
                throw new RuntimeException("Failed to generate token");
            }
        }
        return token;
    }

}
