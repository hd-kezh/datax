package com.asiainfo.datax.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig
        implements WebMvcConfigurer
{
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**").allowCredentials(true).allowedHeaders(new String[] { "*" }).allowedOrigins(new String[] { "*" }).allowedMethods(new String[] { "*" });
    }
}
