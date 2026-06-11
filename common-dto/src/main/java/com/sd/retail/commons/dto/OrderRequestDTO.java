package com.sd.retail.commons.dto;

import com.sd.retail.commons.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Long orderId;

}
