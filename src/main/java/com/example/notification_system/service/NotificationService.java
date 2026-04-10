package com.example.notification_system.service;

import com.example.notification_system.model.Notification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class NotificationService {

    private final RedisTemplate<String, Notification> redisTemplate;

    public NotificationService (RedisTemplate<String,Notification> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void saveNotification(Notification notification){
        String key = "notification:user:"+notification.getUserId();

        redisTemplate.opsForList().leftPush(key,notification);
        redisTemplate.expire(key, Duration.ofDays(7));

    }

    public List<Notification> getNotifications(Long userId){
        String key = "notification:user:"+userId;
        return redisTemplate.opsForList().range(key,0,-1);
    }

    public void markNotificationsRead(Long userId){

        String key = "notifications:user:" + userId;

        List<Notification> list = redisTemplate.opsForList().range(key,0,-1);

        for(Notification n : list){
            n.setRead(true);
        }

        redisTemplate.delete(key);

        redisTemplate.opsForList().leftPushAll(key,list);
    }

    public void saveAll(List<Notification> batch){
        System.out.println("Saving batch of notifications together in DB");
    }

}
