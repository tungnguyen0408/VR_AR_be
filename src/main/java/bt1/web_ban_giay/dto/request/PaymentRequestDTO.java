package bt1.web_ban_giay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Amount is required")
    private Double amount;

    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}