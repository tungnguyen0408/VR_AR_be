package bt1.web_ban_giay.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String notificationType;
    private Boolean isRead;
    private Long relatedEntityId;
    private String relatedEntityType;
}