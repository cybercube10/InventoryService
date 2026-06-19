package com.sd.retail.notification.alert;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredItemsAlert {
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void checkExpiredItemsAlert() {

    }
}
