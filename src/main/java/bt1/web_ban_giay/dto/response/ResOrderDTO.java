package bt1.web_ban_giay.dto.response;

import bt1.web_ban_giay.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResOrderDTO {
    private Long id;
    private Long userId;
    private String customerName;
    private String customerPhone;
    private BigDecimal totalAmount;
    private Order.OrderStatus status;
    private Order.PaymentStatus paymentStatus;
    private String shippingAddress;
    private Long shippingMethodId;
    private BigDecimal shippingFee;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private String couponCode;
    private String paymentMethod;
    private String trackingNumber;
    private LocalDateTime estimatedDeliveryDate;
    private String notes;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
