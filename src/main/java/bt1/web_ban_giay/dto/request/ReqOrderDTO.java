package bt1.web_ban_giay.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReqOrderDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Shipping method ID is required")
    private Long shippingMethodId;

    private BigDecimal shippingFee;

    private BigDecimal taxAmount;

    private BigDecimal discountAmount;

    private String couponCode;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String notes;

    @Valid
    @NotNull(message = "Order items are required")
    private List<OrderItemDTO> items;
}
