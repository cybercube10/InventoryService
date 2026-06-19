package com.sd.retail.commons.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryItemDTO {
    private String productName;
    private LocalDate expiryDate;
    private int quantity;
    private UUID batchId;
}
