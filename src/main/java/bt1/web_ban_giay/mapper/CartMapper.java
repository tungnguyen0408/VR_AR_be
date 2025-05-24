package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.CartDTO;
import bt1.web_ban_giay.entity.Cart;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "items")
    CartDTO toDTO(Cart cart);

    List<CartDTO> toDTOList(List<Cart> carts);
}
