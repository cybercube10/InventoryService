package com.sd.retail.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "category Field CannotBeNull")
    private Long categoryId;
    @NotNull(message = "product cannot be null")
    private String productName;
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "price field cannot be null")
    private BigDecimal costPrice;
    @NotNull(message = "price field cannot be null")
    private BigDecimal sellingPrice;

    private String sku;
}
