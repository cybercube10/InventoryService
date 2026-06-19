package com.sd.retail.inventory.message;

import com.sd.retail.commons.event.ExpiryAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.sd.retail.commons.KafkaConfigProperties.EXPIRY_ALERT_TOPIC;

@Component
@Slf4j
public class ExpiryInventoryProducer {
    private final KafkaTemplate<String, ExpiryAlertEvent> template;
    public ExpiryInventoryProducer(KafkaTemplate<String, ExpiryAlertEvent> template){
        this.template = template;
    }
    public void publishExpiryAlertEvent(ExpiryAlertEvent expiryAlertEvent){
        try{
            log.info("ExpiryInventoryProducer sending ExpiryAlertEvent");
            template.send(EXPIRY_ALERT_TOPIC,expiryAlertEvent);
        }
        catch (Exception e) {
            log.error("Error sending expiry alert event",e.getMessage(),e);
        }
    }
}
