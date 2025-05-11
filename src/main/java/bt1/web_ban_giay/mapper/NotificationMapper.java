package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.NotificationDTO;
import bt1.web_ban_giay.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Notification toEntity(NotificationDTO notificationDTO);

    @Mapping(target = "userId", source = "user.id")
    NotificationDTO toDTO(Notification notification);
}