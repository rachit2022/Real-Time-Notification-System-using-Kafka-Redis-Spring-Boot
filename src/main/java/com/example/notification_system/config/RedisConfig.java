package com.example.notification_system.config;

import com.example.notification_system.model.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Notification> redisTemplate(RedisConnectionFactory connectionFactory){

        RedisTemplate<String,Notification> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;

    }

}
