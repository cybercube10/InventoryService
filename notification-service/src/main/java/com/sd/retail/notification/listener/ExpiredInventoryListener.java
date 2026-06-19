package com.sd.retail.notification.listener;

import com.sd.retail.commons.event.ExpiryAlertEvent;
import com.sd.retail.commons.event.InventoryReserveEvent;
import com.sd.retail.notification.repository.NotificationRepository;
import com.sd.retail.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.sd.retail.commons.KafkaConfigProperties.EXPIRY_ALERT_GROUP;
import static com.sd.retail.commons.KafkaConfigProperties.EXPIRY_ALERT_TOPIC;

@Component
@Slf4j
public class ExpiredInventoryListener {
    private final NotificationService notificationService;
    public ExpiredInventoryListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @KafkaListener(topics = EXPIRY_ALERT_TOPIC,groupId = EXPIRY_ALERT_GROUP)
    public void consumeExpiredInventoryReserveEvent(ExpiryAlertEvent expiredInventoryEvent) {
        log.info("Consuming expired inventory reserve event");
        notificationService.createNotification(expiredInventoryEvent);

        // send mail logic
    }
}
