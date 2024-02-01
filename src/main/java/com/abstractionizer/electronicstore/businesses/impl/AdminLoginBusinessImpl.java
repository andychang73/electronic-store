package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.AdminLoginBusiness;
import com.abstractionizer.electronicstore.businesses.TokenBusiness;
import com.abstractionizer.electronicstore.model.login.AccountInfo;
import com.abstractionizer.electronicstore.model.login.LoginDto;
import com.abstractionizer.electronicstore.service.AdminService;
import com.abstractionizer.electronicstore.service.LoginService;
import com.abstractionizer.electronicstore.service.TokenService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.AdminEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginBusinessImpl implements AdminLoginBusiness {

    private final LoginService loginService;
    private final AdminService adminService;
    private final TokenService tokenService;
    private final TokenBusiness tokenBusiness;


    public AdminLoginBusinessImpl(LoginService loginService, AdminService adminService,
                                  TokenService tokenService, TokenBusiness tokenBusiness) {
        this.loginService = loginService;
        this.adminService = adminService;
        this.tokenService = tokenService;
        this.tokenBusiness = tokenBusiness;
    }

    @Override
    public String login(@NonNull final LoginDto dto) {

        AdminEntity admin = adminService.getAdminByNameOrThrow(dto.getUsername());

        loginService.verifyPasswordOrThrow(dto.getPassword(), admin.getPassword());

        String token = tokenBusiness.checkAndGenerateToken();

        tokenService.associateTokenAndAccount(token, new AccountInfo(admin.getName()));

        return token;
    }
}
