package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.TokenBusiness;
import com.abstractionizer.electronicstore.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenBusinessImplTest {

    @Mock
    TokenService tokenService;

    @Mock
    TokenBusiness tokenBusiness;

    @BeforeEach
    public void init(){
        tokenBusiness = new TokenBusinessImpl(3, tokenService);
    }

    @Test
    public void testCheckAndGenerateToken_SuccessfullyGenerateTheTokenFirstTime(){

        when(tokenService.isTokenExists(anyString())).thenReturn(false);

        String token = tokenBusiness.checkAndGenerateToken();

        assertNotNull(token);
        verify(tokenService, times(1)).isTokenExists(anyString());
    }

    @Test
    public void testCheckAndGenerateToken_SuccessfullyGenerateTheTokenTheSecondTime(){

        when(tokenService.isTokenExists(anyString()))
                .thenReturn(true)
                .thenReturn(false);

        String token = tokenBusiness.checkAndGenerateToken();

        assertNotNull(token);
        verify(tokenService, times(2)).isTokenExists(anyString());
    }

    @Test
    public void testCheckAndGenerateToken_FailedToGenerateToken(){

        when(tokenService.isTokenExists(anyString()))
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> tokenBusiness.checkAndGenerateToken(),
                "Failed to generate Token");


        verify(tokenService, times(3)).isTokenExists(anyString());
    }
}