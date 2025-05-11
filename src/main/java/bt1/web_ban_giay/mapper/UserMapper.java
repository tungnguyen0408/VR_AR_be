package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.ResLoginDTO;
import bt1.web_ban_giay.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResLoginDTO toDto(User user);
}
