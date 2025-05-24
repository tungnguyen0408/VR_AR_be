package bt1.web_ban_giay.dto.request;

import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReqProductDTO {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private BigDecimal price;

    private BigDecimal salePrice;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    private String gender;

    private String brand;

    private String material;

    private String style;

    private String color;

    private String size;

    private String status;

    private String sku;

    private BigDecimal weight;

    private String dimensions;

    private Integer stockQuantity;

    private Boolean isFeatured;

    private Boolean isNew;

    private Boolean isBestseller;

    private String metaTitle;

    private String metaDescription;

    private String metaKeywords;

    private List<String> imageUrls; // Dùng để lưu URL hình ảnh sản phẩm

    private List<ProductVariantDTO> variants; // Dùng để lưu danh sách biến thể sản phẩm
}
