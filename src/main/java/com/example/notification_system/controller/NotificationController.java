package com.example.notification_system.controller;


import com.example.notification_system.enums.EventType;
import com.example.notification_system.model.Notification;
import com.example.notification_system.model.UserEvent;
import com.example.notification_system.producer.EventProducer;
import com.example.notification_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final EventProducer eventProducer;

    @Autowired
    public NotificationService notificationService;

    public NotificationController(EventProducer eventProducer){
        this.eventProducer = eventProducer;
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody UserEvent event){
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType(EventType.ORDER_PLACED.toString());
        event.setTimestamp(LocalDateTime.now());
        event.setNotificationId(UUID.randomUUID().toString());

        eventProducer.sendEvent(event);
        return "Order event sent to kafka";

    }

    @GetMapping("/notifications/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId){
        return notificationService.getNotifications(userId);
    }

    @PostMapping("/notifications/read/{userId}")
    public String markRead(@PathVariable Long userId){

        notificationService.markNotificationsRead(userId);

        return "Notifications marked as read";
    }

}
