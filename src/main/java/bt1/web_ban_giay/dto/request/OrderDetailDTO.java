package bt1.web_ban_giay.dto.request;

import bt1.web_ban_giay.dto.response.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int quantity;
    private int size;
    private ProductDTO product;
}
