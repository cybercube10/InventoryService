package com.sd.retail.commons.dto;

import com.sd.retail.commons.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private String customerPhone;
    private String OrderType;
    private BigDecimal orderAmount;
    private OrderStatus orderStatus;
}
