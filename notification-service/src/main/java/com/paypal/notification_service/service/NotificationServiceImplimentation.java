package com.paypal.notification_service.service;

import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplimentation implements NotificationService {

    private final   NotificationRepository notificationRepository;

    @Override
    public Notification sendNotification(Notification notification) {
        notification.setSentAt(LocalDateTime.now());
        return notificationRepository.save(notification);

    }

    @Override
    public List<Notification> getNotificationByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }
}
