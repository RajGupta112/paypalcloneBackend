package com.paypal.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.entity.Transaction;
import com.paypal.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private  final NotificationRepository notificationRepository;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "txn-initiated",groupId = "notification-group")
    public void listner(Transaction transaction) {

        System.out.println("📥 Received transaction: " + transaction);
        Notification notification = new Notification();
        notification.setUserId(transaction.getSenderId());
        notification.setMessage("💰 ₹" + transaction.getAmount() + " received from user " + transaction.getSenderId());
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        System.out.println("✅ Notification saved: " + notification);





    }
}
