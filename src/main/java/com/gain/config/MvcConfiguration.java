package com.gain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    /**
     * 解决跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 作用于所有接口
//                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:8848") // 建议指定前端域名
                .allowedOriginPatterns("*") // 允许所有源（Spring Boot 2.4+ 推荐用这个代替 allowedOrigins）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization") // 【核心】暴露 Authorization 头部，前端才能在某些环境下读到它
                .allowCredentials(true) // 允许携带凭证（如 Cookie）
                .maxAge(3600); // 预检请求（OPTIONS）的有效期，1小时内不需要重复预检
    }

}
