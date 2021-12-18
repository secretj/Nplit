package com.nplit.Configuration;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nplit.interceptor.AuthenticationInterceptor;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	 @Inject
	 AuthenticationInterceptor authenticationInterceptor;
	 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
        .excludePathPatterns("/upload/**","/assets/**","/error","/login","/loginProcess");
        
    }
}