package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.ResLoginDTO;
import bt1.web_ban_giay.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProductMapper.class, UserResponseMapper.class })
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    ResLoginDTO toDto(User user);
}
