package com.ljz.diagnostic_system.common.Configuration;

import com.ljz.diagnostic_system.common.Interceptor.HeaderTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
拦截器配置类
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
// implements WebMvcConfigurer
    // 多个拦截器组成一个拦截器链
    // addPathPatterns 用于添加拦截规则
    // excludePathPatterns 用户排除拦截

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderTokenInterceptor())//添加拦截器
                .excludePathPatterns("/*","/css/**","/fonts/**","/framework/**","/images/**","/js/**","/layui/**","/tgls/**","/files/**");
                //.excludePathPatterns("/*");
    }
}
