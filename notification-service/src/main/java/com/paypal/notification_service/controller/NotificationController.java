package com.paypal.notification_service.controller;

import com.paypal.notification_service.repository.NotificationRepository;
import com.paypal.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    private NotificationService notificationService;

    public Notification

}
