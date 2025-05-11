package bt1.web_ban_giay.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShippingMethodDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal baseFee;
    private Integer estimatedDays;
    private Boolean isActive;
}