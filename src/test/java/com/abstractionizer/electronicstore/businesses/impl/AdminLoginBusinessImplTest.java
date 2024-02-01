package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.AdminLoginBusiness;
import com.abstractionizer.electronicstore.businesses.TokenBusiness;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.login.AccountInfo;
import com.abstractionizer.electronicstore.model.login.LoginDto;
import com.abstractionizer.electronicstore.service.AdminService;
import com.abstractionizer.electronicstore.service.LoginService;
import com.abstractionizer.electronicstore.service.TokenService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.AdminEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminLoginBusinessImplTest {

    @Mock
    LoginService loginService;

    @Mock
    AdminService adminService;

    @Mock
    TokenService tokenService;

    @Mock
    TokenBusiness tokenBusiness;

    @Mock
    AdminLoginBusiness adminLoginBusiness;

    @BeforeEach
    public void init(){
        adminLoginBusiness = new AdminLoginBusinessImpl(loginService, adminService,
                tokenService, tokenBusiness);
    }

    @Test
    public void testLogin_WhenAdminNameDoesNotExist_ThenThrowBusinessException(){

        doThrow(BusinessException.class)
                .when(adminService)
                .getAdminByNameOrThrow(anyString());

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");

        assertThrows(BusinessException.class, () -> adminLoginBusiness.login(loginDto));

        verify(loginService, never()).verifyPasswordOrThrow(anyString(), anyString());
        verify(tokenBusiness, never()).checkAndGenerateToken();
        verify(tokenService, never()).associateTokenAndAccount(anyString(), any(AccountInfo.class));
    }

    @Test
    public void testLogin_WhenPasswordsDoNotMatch_ThenThrowBusinessException(){

        AdminEntity admin = new AdminEntity();
        admin.setPassword("1");
        when(adminService.getAdminByNameOrThrow(anyString())).thenReturn(admin);

        doThrow(BusinessException.class)
                .when(loginService)
                .verifyPasswordOrThrow(anyString(), anyString());

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("2");
        assertThrows(BusinessException.class, () -> adminLoginBusiness.login(loginDto));

        verify(tokenBusiness, never()).checkAndGenerateToken();
        verify(tokenService, never()).associateTokenAndAccount(anyString(), any(AccountInfo.class));
    }

    @Test
    public void testLogin_WhenAdminNameAndPasswordMatched_ThenReturnToken(){

        AdminEntity admin = new AdminEntity();
        admin.setPassword("1");
        when(adminService.getAdminByNameOrThrow(anyString())).thenReturn(admin);

        when(tokenBusiness.checkAndGenerateToken()).thenReturn(UUID.randomUUID().toString());

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("2");

        String token = adminLoginBusiness.login(loginDto);

        assertNotNull(token);
    }
}