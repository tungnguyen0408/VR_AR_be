package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.OrderItemDTO;
import bt1.web_ban_giay.dto.request.ReqOrderDTO;
import bt1.web_ban_giay.dto.response.ResOrderDTO;
import bt1.web_ban_giay.entity.Order;
import bt1.web_ban_giay.service.OrderService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(orderService.getOrders(filter, pageable));
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
}
