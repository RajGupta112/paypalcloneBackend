package com.paypal.notification_service.controller;

import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.repository.NotificationRepository;
import com.paypal.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private NotificationService notificationService;

    @PostMapping()
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification){
        return ResponseEntity.ok(notificationService.sendNotification(
                notification
        ));



    }

    @GetMapping("/{userId}")
    public List<Notification> getNotification(@PathVariable Long userId){
       return notificationService.getNotificationByUserId(userId);
    }

}
