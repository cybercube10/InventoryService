package com.sd.retail.inventory.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tbl_inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID batchId;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "productId")
    private Product product;

    private Long tenantId;

    private int totalQty;

    private int availableQty;
    private LocalDate expiryDate;
    private LocalDateTime createdDate;

}
