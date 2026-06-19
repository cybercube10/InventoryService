package com.sd.retail.order.controller;


import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.commons.dto.OrderResponseDTO;
import com.sd.retail.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/generate")
    public ResponseEntity<OrderResponseDTO> generateOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO));

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
