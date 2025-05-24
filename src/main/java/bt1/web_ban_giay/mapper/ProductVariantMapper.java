package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import bt1.web_ban_giay.entity.ProductVariant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductVariantMapper {

    @Mapping(target = "productId", source = "product.id")
    ProductVariantDTO toDTO(ProductVariant variant);

    List<ProductVariantDTO> toDTOList(List<ProductVariant> variants);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "sku", ignore = true)
    ProductVariant toEntity(ProductVariantDTO dto);
}