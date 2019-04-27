package com.netease.shopping.configuration;

import com.netease.shopping.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

@Component
@Configuration
public class ShoppingConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    PassportInterceptor passportInterceptor;

    /***/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);  // 在系统初始化的时候加上拦截器
        super.addInterceptors(registry);
    }


}
