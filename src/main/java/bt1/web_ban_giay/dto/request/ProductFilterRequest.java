package bt1.web_ban_giay.dto.request;

import bt1.web_ban_giay.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilterRequest {
    private Long categoryId;
    private String brand;
    private Product.Gender gender;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isBestseller;
    private Boolean isNew;
    private Boolean isFeatured;
}