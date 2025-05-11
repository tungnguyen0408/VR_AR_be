package bt1.web_ban_giay.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Long orderId;
    private Integer rating;
    private String title;
    private String content;
    private Boolean isVerifiedPurchase;
    private Boolean isApproved;
}