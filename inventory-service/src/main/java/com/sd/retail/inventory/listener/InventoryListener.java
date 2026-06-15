package com.sd.retail.inventory.listener;

import com.sd.retail.commons.event.OrderEvent;
import com.sd.retail.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.sd.retail.commons.KafkaConfigProperties.INVENTORY_EVENT_GROUP;
import static com.sd.retail.commons.KafkaConfigProperties.ORDER_BOOKING_EVENTS_TOPIC;

@Component
@Slf4j
public class InventoryListener {
    private final InventoryService inventoryService;
    public InventoryListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @KafkaListener(topics = ORDER_BOOKING_EVENTS_TOPIC,groupId = INVENTORY_EVENT_GROUP)
    public void handleBooking(OrderEvent orderEvent) {
     log.info("Received Order Event reserving stock for order"+orderEvent.getEventId());
     inventoryService.handleBooking(orderEvent);
    }

}
