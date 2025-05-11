package bt1.web_ban_giay.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private ProductDTO product;
    private ProductVariantDTO productVariant;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}