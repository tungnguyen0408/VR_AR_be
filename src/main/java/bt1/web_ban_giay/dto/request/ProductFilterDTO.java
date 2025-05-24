package bt1.web_ban_giay.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFilterDTO {
    private List<String> brands;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<String> colors;
    private List<Integer> sizes;
    private String gender;
    private String sortBy;
    private String sortDirection;
}