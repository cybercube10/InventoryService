package com.sd.retail.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.retail.commons.dto.ExpiryItemDTO;
import com.sd.retail.commons.enums.NotificationType;
import com.sd.retail.commons.event.ExpiryAlertEvent;
import com.sd.retail.notification.model.Notification;
import com.sd.retail.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;
    public NotificationService(NotificationRepository notificationRepository,ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
    }
    public void createNotification(ExpiryAlertEvent expiredInventoryEvent) {
        Notification notification = new Notification();
        if(expiredInventoryEvent.getType().equals(NotificationType.EXPIRED)) {

            notification.setTitle("Stock Expiry Warning");
            try {
                String jsonItems = objectMapper.writeValueAsString(expiredInventoryEvent.getItems());
                notification.setMessage(jsonItems);

            } catch (Exception e) {
                notification.setMessage("[]");
            }
            notification.setType(NotificationType.EXPIRED);
        }
        notification.setTenantId(1L);

    }
}
