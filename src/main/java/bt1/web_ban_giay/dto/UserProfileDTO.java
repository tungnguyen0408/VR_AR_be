package bt1.web_ban_giay.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String username;
    private String fullName;
    private String phone;
    private String email;
    private Long totalOrders;
    private Double totalSpent;
    private String role;
} 