package bt1.web_ban_giay.dto.request;

import bt1.web_ban_giay.entity.Product;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFilterDTO {
    private List<String> brands;
    private Product.Gender gender;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isBestseller;
    private Boolean isNew;
    private Boolean isFeatured;
    private String sortBy;
    private String sortDirection;
    private List<String> sizes;
    private List<String> colors;
}