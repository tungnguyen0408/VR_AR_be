package bt1.web_ban_giay.dto;

import lombok.Data;
import java.util.List;

@Data
public class WishlistDTO {
    private Long id;
    private Long userId;
    private List<Long> productIds;
}