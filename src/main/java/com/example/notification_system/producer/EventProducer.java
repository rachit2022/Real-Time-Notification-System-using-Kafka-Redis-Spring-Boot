package com.example.notification_system.producer;

import com.example.notification_system.model.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public EventProducer(KafkaTemplate<String,UserEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEvent event){
        kafkaTemplate.send(TOPIC,event.getUserId().toString(),event);
        System.out.println("Event sent: "+event.getEventId());
    }


}
