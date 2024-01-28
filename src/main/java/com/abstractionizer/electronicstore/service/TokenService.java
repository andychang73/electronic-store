package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.model.login.AccountInfo;

public interface TokenService {

    boolean isTokenExists(String token);

    void associateTokenAndAccount(String key, AccountInfo accountInfo);

    AccountInfo getAccountInfoByTokenOrThrow(String token);
}
