package com.abstractionizer.electronicstore.interceptors;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.login.AccountInfo;
import com.abstractionizer.electronicstore.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.Set;

import static com.abstractionizer.electronicstore.constant.HeaderKey.H_KEY_TOKEN;
import static com.abstractionizer.electronicstore.errors.Error.AUTH_ERROR;

@Service
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public AdminAuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = Optional.ofNullable(request.getHeader(H_KEY_TOKEN)).orElseThrow(() -> new BusinessException(AUTH_ERROR));

        AccountInfo account = tokenService.getAccountInfoByTokenOrThrow(token);

        User user = new User(account.getUsername(), "", Set.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()));

        return true;
    }
}
