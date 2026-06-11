package com.sd.retail.commons.event;

import com.sd.retail.commons.dto.OrderRequestDTO;
import com.sd.retail.commons.enums.OrderStatus;

import java.util.Date;
import java.util.UUID;

public interface Event {
    UUID getEventId();
    Date getDate();
}
