package com.sd.retail.inventory.message;

import com.sd.retail.commons.event.InventoryReserveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.sd.retail.commons.KafkaConfigProperties.INVENTORY_RESERVED_TOPIC;

@Component
@Slf4j
public class InventoryReserveProducer {
    private final KafkaTemplate<String, InventoryReserveEvent> template;
    public InventoryReserveProducer(KafkaTemplate<String, InventoryReserveEvent> template) {
        this.template = template;
    }

    public void publishInventoryReserveEvent(InventoryReserveEvent inventoryReserveEvent) {
    try{
        log.info("Publishing inventory reserve event");
        template.send(INVENTORY_RESERVED_TOPIC,String.valueOf(inventoryReserveEvent.getOrderId()),inventoryReserveEvent);
    }
    catch(Exception e){
        log.error("Error publishing inventory reserve event",e.getMessage());
    }
    }

}
