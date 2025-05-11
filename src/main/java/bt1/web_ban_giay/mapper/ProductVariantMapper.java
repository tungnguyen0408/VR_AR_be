package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import bt1.web_ban_giay.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(target = "productId", source = "product.id")
    ProductVariantDTO toDTO(ProductVariant productVariant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductVariant toEntity(ProductVariantDTO productVariantDTO);
}