package com.sd.retail.commons.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter

public class OrderItemDTO {
    private UUID batchId;
    private int quantity;
}
