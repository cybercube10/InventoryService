package com.sd.retail.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponseDTO {
    private UUID batchId;
    private BigDecimal price;
    private String productName;
    private int availableQuantity;
    private LocalDateTime expiryDate;
}
