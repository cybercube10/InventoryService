package com.sd.retail.commons.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class InventoryReserveEvent{

    private Long orderId;
    private boolean reserved;
    private String reason;

}
