package com.sd.retail.commons.event;

import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.commons.enums.OrderStatus;
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
public class OrderEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        this.orderRequestDTO = orderRequestDTO;
        this.orderStatus = orderStatus;
    }

}
