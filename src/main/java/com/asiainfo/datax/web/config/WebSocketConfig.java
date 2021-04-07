package com.asiainfo.datax.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer
{
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        config.enableSimpleBroker(new String[] { "/task", "/monitor" });
    }

    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint(new String[] { "/websocket" }).setAllowedOrigins(new String[] { "*" }).withSockJS();
    }
}
