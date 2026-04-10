package com.example.notification_system.consumer;

import com.example.notification_system.model.Notification;
import com.example.notification_system.model.UserEvent;
import com.example.notification_system.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class DatabaseConsumer {

    private final NotificationService notificationService;

    private final List<Notification> batch = new ArrayList<>();

    public DatabaseConsumer(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-events", groupId = "db-group")
    public void dbConsume(UserEvent event){
        Notification notification = new Notification();

        notification.setNotificationId(event.getNotificationId());
        notification.setMessage(event.getMessage());
        notification.setUserId(event.getUserId());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        batch.add(notification);

        if(batch.size()>=100){
            System.out.println("Inserting the values in DB");
            notificationService.saveAll(batch);
        }


    }

    @Scheduled(fixedRate = 5000)
    public void flushBatch(){
        if(!batch.isEmpty()){
            System.out.println("Inserting the values in DB through scheduler");
            notificationService.saveAll(batch);
            batch.clear();
        }
    }

}
