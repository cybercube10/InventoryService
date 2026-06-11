package com.sd.retail.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderItemDTO {
    private Long orderId;

    private Long productId;

    private UUID batchId;

    private int quantity;

    private double unitPrice;

}
