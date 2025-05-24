package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.request.ReqProductDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.entity.ProductImage;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { ProductVariantMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "imageUrls", source = "images", qualifiedByName = "mapImageUrls")
    @Mapping(target = "variants", source = "variants")
    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOList(List<Product> products);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "slug", ignore = true)
    Product toEntity(ReqProductDTO dto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateEntityFromDto(ReqProductDTO dto, @MappingTarget Product product);

    @Named("mapImageUrls")
    default List<String> mapImageUrls(List<ProductImage> images) {
        if (images == null)
            return null;
        return images.stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
    }
}
