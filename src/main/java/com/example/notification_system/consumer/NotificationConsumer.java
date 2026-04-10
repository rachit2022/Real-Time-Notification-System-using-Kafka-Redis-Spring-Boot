package com.example.notification_system.consumer;

import com.example.notification_system.model.Notification;
import com.example.notification_system.model.UserEvent;
import com.example.notification_system.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEvent event){

        Notification notification = new Notification();

        notification.setNotificationId(event.getNotificationId());
        notification.setUserId(event.getUserId());
        notification.setMessage(buildMessage(event));
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        notificationService.saveNotification(notification);

        System.out.println("Notification created for user " + event.getUserId());
        System.out.println("Notification saved in Redis: " + notification.getNotificationId()+" "+notification.getMessage()+" "+notification.getUserId()+" "+notification.getCreatedAt());


    }

    private String buildMessage(UserEvent event){

        return switch (event.getEventType()) {
            case "ORDER_PLACED" -> "Your order has been placed successfully";
            case "PAYMENT_SUCCESS" -> "Payment completed successfully";
            case "LOGIN" -> "New login detected";
            default -> "New activity detected";
        };
    }

}
