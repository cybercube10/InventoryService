package com.sd.retail.order.model;

import com.sd.retail.commons.enums.OrderStatus;
import com.sd.retail.order.enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private Long tenantId;

    private String customerName;
    private String customerPhone;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    private double totalAmount;

    private double paidAmount;
    private double dueAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;


}
