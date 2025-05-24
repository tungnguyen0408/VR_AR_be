package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.request.ReqOrderDTO;
import bt1.web_ban_giay.dto.response.ResOrderDTO;
import bt1.web_ban_giay.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class, ShippingMethodMapper.class })
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "shippingMethod", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(ReqOrderDTO orderDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "shippingMethodId", source = "shippingMethod.id")
    @Mapping(target = "items", source = "items")
    ResOrderDTO toDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "shippingMethod", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ReqOrderDTO orderDTO, @MappingTarget Order order);
}
