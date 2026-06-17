package com.sd.retail.order.controller;


import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/generate")
    public ResponseEntity<String> generateOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        String response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(response);

    }
}
