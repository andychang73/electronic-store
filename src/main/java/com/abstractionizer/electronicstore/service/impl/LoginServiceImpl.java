package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.service.LoginService;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.abstractionizer.electronicstore.errors.Error.INVALID_ACCOUNT_OR_PASSWORD;

@Service
public class LoginServiceImpl implements LoginService {

    private final BCryptPasswordEncoder encoder;

    public LoginServiceImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void verifyPasswordOrThrow(@NonNull final String inputPassword, @NonNull final String dbPassword) {
        if(Boolean.FALSE == encoder.matches(inputPassword, dbPassword)){
            throw new BusinessException(INVALID_ACCOUNT_OR_PASSWORD);
        }
    }
}
