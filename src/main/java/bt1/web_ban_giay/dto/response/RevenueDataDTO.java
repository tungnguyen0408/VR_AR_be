package bt1.web_ban_giay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDataDTO {
    private String date;
    private BigDecimal revenue;
    private Long orders;
} 