package bt1.web_ban_giay.dto;

import lombok.Data;

@Data
public class ProductAttributeDTO {
    private Long id;
    private String name;
    private String value;
    private Long productId;
}