package com.sd.retail.order;

import com.sd.retail.commons.event.InventoryEvent;
import com.sd.retail.order.messaging.OrderEventProducer;
import com.sd.retail.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.sd.retail.commons.KafkaConfigProperties.INVENTORY_RESERVED_TOPIC;
import static com.sd.retail.commons.KafkaConfigProperties.ORDER_BOOKING_GROUP;

@Component
@Slf4j
public class OrderBookingListener {
   private final OrderService orderService;

   public OrderBookingListener(OrderService orderService) {
       this.orderService = orderService;
   }

   @KafkaListener(topics=INVENTORY_RESERVED_TOPIC, groupId = ORDER_BOOKING_GROUP)
    public void consumeInventoryReserveEvents(InventoryEvent inventoryEvent) {
       if(inventoryEvent.isReserved()){
           log.info("inventory reserved for order"+inventoryEvent.getOrderId());
       }
       else {
           log.info("order failed for orderId "+inventoryEvent.getOrderId());
           orderService.handleOrderOnStockReservationFailure(inventoryEvent.getOrderId());
       }
   }
}
