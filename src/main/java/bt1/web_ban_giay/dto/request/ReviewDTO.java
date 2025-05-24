package bt1.web_ban_giay.dto.request;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long userId;
    private Long productId;
    private String comment;
    private Integer rating;
    private String title;
}