package com.sd.retail.order.messaging;


import com.sd.retail.commons.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.sd.retail.commons.KafkaConfigProperties.ORDER_BOOKING_EVENTS_TOPIC;


@Component
@Slf4j
public class OrderEventProducer {
private KafkaTemplate<String,Object> kafkaTemplate;

public OrderEventProducer(KafkaTemplate<String,Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
}

public void publishOrderEvent(OrderEvent orderEvent){
    try{
        log.info("Order Event Publisher Started");
        kafkaTemplate.send(ORDER_BOOKING_EVENTS_TOPIC,orderEvent);
    }
    catch(Exception ex){
        log.error(ex.getMessage(),ex);
    }
}

}