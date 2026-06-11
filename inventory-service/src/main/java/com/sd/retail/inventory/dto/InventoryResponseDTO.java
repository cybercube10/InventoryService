package com.sd.retail.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InventoryResponseDTO {
    private UUID batchId;
    private Long productId;
    private String productName;
    private String sku;
    private Integer totalQty;
    private Integer availableQty;
    private LocalDate expiryDate;
    private LocalDateTime createdDate;
}
