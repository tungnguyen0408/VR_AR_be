package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.WishlistDTO;
import bt1.web_ban_giay.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "products", ignore = true)
    Wishlist toEntity(WishlistDTO wishlistDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productIds", expression = "java(wishlist.getProducts().stream().map(product -> product.getId()).collect(java.util.stream.Collectors.toList()))")
    WishlistDTO toDTO(Wishlist wishlist);
}