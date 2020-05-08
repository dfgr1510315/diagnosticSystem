package com.ljz.diagnostic_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DiagnosticSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiagnosticSystemApplication.class, args);
    }

 /*   @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //设置路径xxx
        factory.setLocation("/xxx");
        return factory.createMultipartConfig();
    }*/
}
