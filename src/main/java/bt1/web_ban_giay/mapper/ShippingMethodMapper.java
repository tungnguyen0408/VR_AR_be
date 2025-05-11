package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.ShippingMethodDTO;
import bt1.web_ban_giay.entity.ShippingMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMethodMapper {
    ShippingMethodDTO toDTO(ShippingMethod shippingMethod);

    ShippingMethod toEntity(ShippingMethodDTO shippingMethodDTO);
}