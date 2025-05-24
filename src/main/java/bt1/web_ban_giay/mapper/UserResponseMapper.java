package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.UserDTO;
import bt1.web_ban_giay.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "avatarUrl", source = "avatarUrl")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "lastLogin", source = "lastLogin")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserDTO toDTO(User user);
}