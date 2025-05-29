package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.OrderItemDTO;
import bt1.web_ban_giay.dto.request.ReqOrderDTO;
import bt1.web_ban_giay.dto.request.PaymentRequestDTO;
import bt1.web_ban_giay.dto.response.ResOrderDTO;
import bt1.web_ban_giay.entity.Order;
import bt1.web_ban_giay.service.OrderService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ResOrderDTO> createOrder(@Valid @RequestBody ReqOrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ResOrderDTO>> getOrders(
            @Filter String filter,
            Pageable pageable) {
        String safeFilter = filter != null ? filter : "";
        return ResponseEntity.ok(orderService.getOrders(safeFilter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResOrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResOrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam @NotNull Order.OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<ResOrderDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam @NotNull Order.PaymentStatus status) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(id, status));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ResOrderDTO> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ResOrderDTO>> getOrdersByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ResOrderDTO>> getOrdersByStatus(
            @PathVariable Order.OrderStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status, pageable));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<ResOrderDTO> processPayment(
            @PathVariable Long id,
            @RequestBody PaymentRequestDTO paymentRequest) {
        return ResponseEntity.ok(orderService.processPayment(id, paymentRequest));
    }

    @PutMapping("/{id}/shipping-status")
    public ResponseEntity<ResOrderDTO> updateShippingStatus(
            @PathVariable Long id,
            @RequestParam @NotNull String trackingNumber,
            @RequestParam @NotNull Order.ShippingStatus status) {
        return ResponseEntity.ok(orderService.updateShippingStatus(id, trackingNumber, status));
    }

    @PostMapping("/{id}/apply-coupon")
    public ResponseEntity<ResOrderDTO> applyCoupon(
            @PathVariable Long id,
            @RequestParam @NotNull String couponCode) {
        return ResponseEntity.ok(orderService.applyCoupon(id, couponCode));
    }

    @GetMapping("/calculate-shipping")
    public ResponseEntity<BigDecimal> calculateShippingFee(
            @RequestParam @NotNull Long shippingMethodId,
            @RequestParam @NotNull String address) {
        return ResponseEntity.ok(orderService.calculateShippingFee(shippingMethodId, address));
    }
}
