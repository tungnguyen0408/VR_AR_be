package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.request.OrderItemDTO;
import bt1.web_ban_giay.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    @Mapping(target = "productId", source = "product.id")
    OrderItemDTO toDTO(OrderItem orderItem);
}