package com.sd.retail.inventory.scheduler;

import com.sd.retail.commons.dto.ExpiryItemDTO;
import com.sd.retail.commons.event.ExpiryAlertEvent;
import com.sd.retail.inventory.message.ExpiryInventoryProducer;
import com.sd.retail.inventory.service.InventoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InventoryExpiryScheduler {
    private final InventoryService inventoryService;
    private final ExpiryInventoryProducer expiryInventoryProducer;
    public InventoryExpiryScheduler(InventoryService inventoryService,ExpiryInventoryProducer expiryInventoryProducer) {
        this.expiryInventoryProducer = expiryInventoryProducer;
        this.inventoryService = inventoryService;
    }
    @Scheduled(cron = "0 0 9 * * *",zone = "Asia/Kolkata")
    public void checkExpiredItemsAlert() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        List<ExpiryItemDTO> items = inventoryService.findItemsByExpiryDateBetween(startDate,endDate);
        ExpiryAlertEvent expiryAlertEvent = new ExpiryAlertEvent();
        expiryAlertEvent.setItems(items);
        expiryAlertEvent.setTenantId(1L);
        expiryInventoryProducer.publishExpiryAlertEvent(expiryAlertEvent);
    }
}
