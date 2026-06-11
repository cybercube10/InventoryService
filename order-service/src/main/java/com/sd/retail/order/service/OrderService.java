package com.sd.retail.order.service;

import com.sd.retail.commons.enums.OrderStatus;
import com.sd.retail.commons.dto.OrderItemDTO;
import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.order.exception.InsufficientStockException;
import com.sd.retail.order.repository.OrderItemRepository;
import com.sd.retail.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import com.sd.retail.order.model.Order;
import com.sd.retail.order.model.OrderItem;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    long tenantId = 1L;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,OrderStatusPublisher orderStatusPublisher) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusPublisher = orderStatusPublisher;
    }

    @Transactional
    public String createOrder(OrderRequestDTO dto) {

        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerPhone(dto.getCustomerPhone());
        order.setTenantId(tenantId);

        order.setPaidAmount(dto.getPaidAmount());
        order.setDueAmount(dto.getDueAmount());

    int totalAmt = 0;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : dto.getOrderItems()) {

            boolean  available = inventoryClient.isStockAvailable(orderItemDTO.getBatchId(),orderItemDTO.getQuantity());

            if(!available){
                throw new InsufficientStockException("Insufficient stock for"+orderItemDTO.getBatchId());
            }
            else totalAmt += inventoryClient.getPrice(orderItemDTO.getBatchId()) * orderItemDTO.getQuantity();
        }
        order.setTotalAmount(totalAmt);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.SUCCESS);
        Order savedOrder = orderRepository.save(order);

        order.setItems(items);
        for(OrderItemDTO item : dto.getOrderItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
//            orderItem.setProductId(item.getProductId());
            orderItem.setBatchId(item.getBatchId());
            orderItem.setQuantity(item.getQuantity());
//            orderItem.setPricePerUnit(item.getUnitPrice());
            orderItemRepository.save(orderItem);
        }
         // publish kafka topic here of order created
        dto.setOrderId(savedOrder.getOrderId());
        orderStatusPublisher.publishOrderEvent(dto,OrderStatus.ORDER_CREATED);
        return "Order created: " + savedOrder.getOrderId();
    }
}


