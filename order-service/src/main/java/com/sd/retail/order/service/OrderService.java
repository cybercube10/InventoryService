package com.sd.retail.order.service;

import com.sd.retail.commons.enums.OrderStatus;
import com.sd.retail.commons.dto.OrderItemDTO;
import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.commons.event.OrderEvent;
import com.sd.retail.order.exception.InsufficientStockException;
import com.sd.retail.order.messaging.OrderEventProducer;
import com.sd.retail.order.repository.OrderItemRepository;
import com.sd.retail.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import com.sd.retail.order.model.Order;
import com.sd.retail.order.model.OrderItem;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
public class OrderService {
    long tenantId = 1L;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public String createOrder(OrderRequestDTO dto) {

        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerPhone(dto.getCustomerPhone());
        order.setTenantId(tenantId);
        order.setTotalAmount(dto.getTotalAmount());
        order.setPaidAmount(dto.getPaidAmount());
        order.setDueAmount(dto.getDueAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER_CREATED);
        Order savedOrder = orderRepository.save(order);

        for(OrderItemDTO item : dto.getOrderItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
//            orderItem.setProductId(item.getProductId());
            orderItem.setBatchId(item.getBatchId());
            orderItem.setQuantity(item.getQuantity());
//            orderItem.setPricePerUnit(item.getUnitPrice());
            orderItemRepository.save(orderItem);
        }
        //set id
        dto.setOrderId(savedOrder.getOrderId());
        //create event
        OrderEvent orderEvent = new OrderEvent(dto,OrderStatus.ORDER_CREATED);
         // publish kafka topic here of order created
        orderEventProducer.publishOrderEvent(orderEvent);
        return "Order created: " + savedOrder.getOrderId() + savedOrder.getStatus();
    }

    public void handleOrderOnStockReservationFailure(Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
        if(order != null){
            order.setStatus(OrderStatus.ORDER_CANCELLED);
            orderRepository.save(order);
            log.info("Order Failed: " + order.getOrderId());
        }
        else {
            log.warn("Order not found {}", orderId);
            return;
        }
    }

    public void handleOrderStatus(Long orderId, boolean reserved) {
        Order order = orderRepository.findByOrderId(orderId);
    if(reserved){
        order.setStatus(OrderStatus.ORDER_COMPLETED);
    }
    else {
        order.setStatus(OrderStatus.ORDER_CANCELLED);
    }

    }
}


