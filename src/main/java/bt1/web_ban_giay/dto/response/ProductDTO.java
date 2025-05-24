package bt1.web_ban_giay.dto.response;

import bt1.web_ban_giay.entity.Product;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private String slug;
    private String description;

    @NotNull(message = "Giá sản phẩm không được để trống")
    private BigDecimal price;

    private BigDecimal salePrice;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    private String categoryName;
    private String brand;
    private String sku;
    private BigDecimal weight;
    private String dimensions;
    private String material;

    @Min(value = 0, message = "Số lượng tồn kho không hợp lệ")
    private Integer stockQuantity;

    private Boolean isFeatured;
    private Integer viewCount;
    private Boolean isNew;
    private Boolean isBestseller;

    private BigDecimal ratingAverage;

    private Product.ProductStatus status;

    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;

    private List<String> imageUrls;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Product.Gender gender;

    private List<ProductVariantDTO> variants;

}
