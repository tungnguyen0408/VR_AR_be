package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.CartItemDTO;
import bt1.web_ban_giay.entity.CartItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProductMapper.class,
        ProductVariantMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "productVariant", source = "productVariant")
    CartItemDTO toDTO(CartItem item);

    List<CartItemDTO> toDTOList(List<CartItem> items);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "productVariant", ignore = true)
    CartItem toEntity(CartItemDTO dto);
}