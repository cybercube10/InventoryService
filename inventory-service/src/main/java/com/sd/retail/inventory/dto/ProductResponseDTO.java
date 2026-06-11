package com.sd.retail.inventory.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long categoryId;
    private String productName;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private String sku;
}
