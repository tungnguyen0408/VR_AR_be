package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.request.ReqProductDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


    @Mapper(componentModel = "spring", uses = { ProductVariantMapper.class })
    public interface ProductMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "category", ignore = true)
        @Mapping(target = "images", ignore = true)
        @Mapping(target = "variants", ignore = true)
        @Mapping(target = "attributes", ignore = true)
        @Mapping(target = "tags", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "slug", ignore = true)
        @Mapping(target = "sku", ignore = true)
        @Mapping(target = "stockQuantity", ignore = true)
        @Mapping(target = "isFeatured", ignore = true)
        @Mapping(target = "viewCount", ignore = true)
        @Mapping(target = "isNew", ignore = true)
        @Mapping(target = "isBestseller", ignore = true)
        @Mapping(target = "ratingAverage", ignore = true)
        @Mapping(target = "metaTitle", ignore = true)
        @Mapping(target = "metaDescription", ignore = true)
        @Mapping(target = "metaKeywords", ignore = true)
        Product toEntity(ReqProductDTO productDTO);

        @Mapping(target = "categoryId", source = "category.id")
        @Mapping(target = "categoryName", source = "category.name")
        @Mapping(target = "variants", source = "variants")
        ProductDTO toDTO(Product product);

        List<ProductDTO> toDTOList(List<Product> products);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "category", ignore = true)
        @Mapping(target = "images", ignore = true)
        @Mapping(target = "variants", ignore = true)
        @Mapping(target = "attributes", ignore = true)
        @Mapping(target = "tags", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "slug", ignore = true)
        @Mapping(target = "sku", ignore = true)
        @Mapping(target = "stockQuantity", ignore = true)
        @Mapping(target = "isFeatured", ignore = true)
        @Mapping(target = "viewCount", ignore = true)
        @Mapping(target = "isNew", ignore = true)
        @Mapping(target = "isBestseller", ignore = true)
        @Mapping(target = "ratingAverage", ignore = true)
        @Mapping(target = "metaTitle", ignore = true)
        @Mapping(target = "metaDescription", ignore = true)
        @Mapping(target = "metaKeywords", ignore = true)
        void updateEntityFromDto(ReqProductDTO productDTO, @MappingTarget Product product);
    }

