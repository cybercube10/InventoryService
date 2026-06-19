package com.sd.retail.commons.dto;

import com.sd.retail.commons.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private String customerPhone;
    private String OrderType;
    private BigDecimal orderAmount;
    private OrderStatus orderStatus;

    public OrderResponseDTO(long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.orderStatus = status;
    }
}
