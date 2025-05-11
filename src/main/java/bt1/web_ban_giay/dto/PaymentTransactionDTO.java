package bt1.web_ban_giay.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentTransactionDTO {
    private Long id;
    private Long orderId;
    private String transactionId;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private String paymentDetails;
    private String errorMessage;
}