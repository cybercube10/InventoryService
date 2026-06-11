package com.sd.retail.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequestDTO {
    @NotNull
    private Long tenantId;

    @NotNull(message ="customer name is required")
    private String customerName;

    @NotNull(message = "phone number is required")
    private String customerPhone;

    private double totalAmount;

    private double paidAmount;

    private double dueAmount;

    private List<OrderItemDTO> orderItems;

}
