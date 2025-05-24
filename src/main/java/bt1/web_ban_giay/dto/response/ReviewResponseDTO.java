package bt1.web_ban_giay.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private String title;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}