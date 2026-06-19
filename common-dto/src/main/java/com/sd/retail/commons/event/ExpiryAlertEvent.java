package com.sd.retail.commons.event;

import com.sd.retail.commons.dto.ExpiryItemDTO;
import com.sd.retail.commons.enums.NotificationType;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpiryAlertEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private List<ExpiryItemDTO> items;
    private Long tenantId;
    private NotificationType type = NotificationType.EXPIRED;
    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }
}
