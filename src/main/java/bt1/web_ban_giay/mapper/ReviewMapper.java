package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.request.ReviewDTO;
import bt1.web_ban_giay.dto.response.ReviewResponseDTO;
import bt1.web_ban_giay.entity.Review;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserResponseMapper.class,
        ProductMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    ReviewResponseDTO toDTO(Review review);

    List<ReviewResponseDTO> toDTOList(List<Review> reviews);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    Review toEntity(ReviewDTO dto);
}