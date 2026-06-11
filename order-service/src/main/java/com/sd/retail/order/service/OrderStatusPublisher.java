package com.sd.retail.order.service;

import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.commons.event.OrderEvent;
import com.sd.retail.commons.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {
    @Autowired
    private Sinks.Many<OrderEvent> orderSink;

    public void publishOrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDTO,orderStatus);
        orderSink.tryEmitNext(orderEvent);

    }
}
