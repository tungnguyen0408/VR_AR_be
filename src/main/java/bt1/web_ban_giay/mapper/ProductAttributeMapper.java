package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.ProductAttributeDTO;
import bt1.web_ban_giay.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductAttribute toEntity(ProductAttributeDTO productAttributeDTO);

    @Mapping(target = "productId", source = "product.id")
    ProductAttributeDTO toDTO(ProductAttribute productAttribute);
}