package com.sd.retail.inventory.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private Integer totalQty;

    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
}
