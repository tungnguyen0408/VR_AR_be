package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.ProductTagDTO;
import bt1.web_ban_giay.entity.ProductTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    ProductTag toEntity(ProductTagDTO productTagDTO);

    ProductTagDTO toDTO(ProductTag productTag);
}