package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // Lấy đơn hàng theo user
    List<Order> findByUserId(Long userId);

    // Lấy đơn hàng theo trạng thái
    List<Order> findByStatus(Order.OrderStatus status);

    // Lấy đơn hàng theo trạng thái và user
    List<Order> findByStatusAndUserId(Order.OrderStatus status, Long userId);

    // Lấy đơn hàng theo khoảng thời gian
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Lấy đơn hàng theo trạng thái thanh toán
    List<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus);

    // Lấy đơn hàng theo trạng thái thanh toán và user
    List<Order> findByPaymentStatusAndUserId(Order.PaymentStatus paymentStatus, Long userId);

    // Lấy đơn hàng theo phương thức thanh toán
    List<Order> findByPaymentMethod(String paymentMethod);

    // Lấy đơn hàng theo phương thức vận chuyển
    List<Order> findByShippingMethodId(Long shippingMethodId);

    // Lấy đơn hàng theo mã giảm giá
    List<Order> findByCouponCode(String couponCode);

    // Lấy đơn hàng theo số điện thoại khách hàng
    List<Order> findByCustomerPhone(String customerPhone);

    // Lấy đơn hàng theo tên khách hàng
    List<Order> findByCustomerNameContaining(String customerName);

    // Lấy đơn hàng theo tên và số điện thoại khách hàng
    List<Order> findByCustomerNameContainingAndCustomerPhone(String customerName, String customerPhone);

    // Lấy đơn hàng theo địa chỉ giao hàng
    List<Order> findByShippingAddressContaining(String shippingAddress);

    // Lấy đơn hàng theo số tracking
    List<Order> findByTrackingNumber(String trackingNumber);

    // Lấy đơn hàng theo tổng tiền
    List<Order> findByTotalAmountGreaterThanEqual(java.math.BigDecimal amount);

    // Lấy đơn hàng theo tổng tiền trong khoảng
    List<Order> findByTotalAmountBetween(java.math.BigDecimal minAmount, java.math.BigDecimal maxAmount);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);

    Page<Order> findByPaymentStatus(Order.PaymentStatus status, Pageable pageable);

    List<Order> findByUserIdAndStatus(Long userId, Order.OrderStatus status);

    Page<Order> findByUserIdAndStatus(Long userId, Order.OrderStatus status, Pageable pageable);

    List<Order> findByUserIdAndPaymentStatus(Long userId, Order.PaymentStatus status);

    Page<Order> findByUserIdAndPaymentStatus(Long userId, Order.PaymentStatus status, Pageable pageable);

    Long countByUserId(Long userId);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.user.id = :userId")
    Double sumTotalAmountByUserId(Long userId);
}
