package com.paypal.notification_service.service;


import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.repository.NotificationRepository;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(Notification notification);

    List<Notification> getNotificationByUserId(String userId);
}
