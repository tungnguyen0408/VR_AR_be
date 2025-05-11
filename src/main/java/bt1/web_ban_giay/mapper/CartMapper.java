package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.CartDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.entity.Cart;
import bt1.web_ban_giay.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDto(Cart cart);

    List<CartDTO> toList(List<Cart> carts);

}
