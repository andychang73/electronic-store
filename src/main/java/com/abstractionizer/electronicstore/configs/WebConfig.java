package com.abstractionizer.electronicstore.configs;

import com.abstractionizer.electronicstore.interceptors.AdminAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminAuthInterceptor adminAuthInterceptor;

    public WebConfig(AdminAuthInterceptor adminAuthInterceptor) {
        this.adminAuthInterceptor = adminAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(adminAuthInterceptor).order(Ordered.HIGHEST_PRECEDENCE);
        registration.addPathPatterns("/deal/{type}");
        registration.addPathPatterns("/product");
        registration.addPathPatterns("/product/{productId}");
    }
}
