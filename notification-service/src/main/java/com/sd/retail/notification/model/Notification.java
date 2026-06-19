package com.sd.retail.notification.model;

import com.sd.retail.commons.dto.ExpiryItemDTO;
import com.sd.retail.commons.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;
    private Long tenantId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private List<ExpiryItemDTO> expiredItemDTOList;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

}
