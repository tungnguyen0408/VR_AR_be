package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.request.OrderItemDTO;
import bt1.web_ban_giay.dto.request.ReqOrderDTO;
import bt1.web_ban_giay.dto.request.PaymentRequestDTO;
import bt1.web_ban_giay.dto.response.ResOrderDTO;
import bt1.web_ban_giay.entity.*;
import bt1.web_ban_giay.exception.InvalidException;
import bt1.web_ban_giay.mapper.OrderMapper;
import bt1.web_ban_giay.repository.*;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShippingMethodRepository shippingMethodRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;

    @Transactional
    public ResOrderDTO createOrder(ReqOrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy người dùng"));

        ShippingMethod shippingMethod = shippingMethodRepository.findById(orderDTO.getShippingMethodId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy phương thức vận chuyển"));

        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerPhone(orderDTO.getCustomerPhone());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setShippingMethod(shippingMethod);
        order.setShippingFee(shippingMethod.getBaseFee());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setNotes(orderDTO.getNotes());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);

        // Tính toán tổng tiền và tạo order items
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderItem> orderItems = orderDTO.getItems().stream()
                .map(itemDTO -> {
                    Product product = productRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));
                    ProductVariant variant = productVariantRepository.findById(itemDTO.getVariantId())
                            .orElseThrow(() -> new InvalidException("Không tìm thấy biến thể sản phẩm"));

                    // Kiểm tra số lượng tồn kho
                    if (variant.getStockQuantity() < itemDTO.getQuantity()) {
                        throw new InvalidException("Số lượng sản phẩm không đủ");
                    }

                    // Cập nhật số lượng tồn kho
                    variant.setStockQuantity(variant.getStockQuantity() - itemDTO.getQuantity());
                    productVariantRepository.save(variant);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setProductName(product.getName());
                    orderItem.setProductSku(variant.getSku());
                    orderItem.setQuantity(itemDTO.getQuantity());
                    orderItem.setUnitPrice(product.getPrice());
                    orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
                    orderItem.setDiscountAmount(itemDTO.getDiscountAmount());
                    orderItem.setTaxAmount(itemDTO.getTaxAmount());

                    totalAmount.updateAndGet(current -> current
                            .add(orderItem.getTotalPrice())
                            .subtract(orderItem.getDiscountAmount())
                            .add(orderItem.getTaxAmount()));

                    return orderItem;
                })
                .toList();

        order.setTotalAmount(totalAmount.get());
        order.setEstimatedDeliveryDate(LocalDate.now().plusDays(shippingMethod.getEstimatedDays()));

        Order savedOrder = orderRepository.save(order);
        orderItems.forEach(orderItemRepository::save);

        return orderMapper.toDTO(savedOrder);
    }

    public Page<ResOrderDTO> getOrders(String filter, Pageable pageable) {
        if (filter == null || filter.trim().isEmpty()) {
            return orderRepository.findAll(pageable)
                    .map(orderMapper::toDTO);
        }
        Specification<Order> spec = filterSpecificationConverter.convert(filter);
        return orderRepository.findAll(spec, pageable)
                .map(orderMapper::toDTO);
    }

    public ResOrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public ResOrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public ResOrderDTO updatePaymentStatus(Long id, Order.PaymentStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));
        order.setPaymentStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public ResOrderDTO cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new InvalidException("Không thể hủy đơn hàng đã xử lý");
        }

        // Hoàn trả số lượng tồn kho
        order.getItems().forEach(item -> {
            ProductVariant variant = productVariantRepository.findByProductIdAndSku(
                    item.getProduct().getId(), item.getProductSku())
                    .orElseThrow(() -> new InvalidException("Không tìm thấy biến thể sản phẩm"));
            variant.setStockQuantity(variant.getStockQuantity() + item.getQuantity());
            productVariantRepository.save(variant);
        });

        order.setStatus(Order.OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    public Page<ResOrderDTO> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toDTO);
    }

    public Page<ResOrderDTO> getOrdersByStatus(Order.OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(orderMapper::toDTO);
    }

    // Thêm các chức năng mới

    public List<ResOrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<ResOrderDTO> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<ResOrderDTO> getOrdersByPaymentMethod(String paymentMethod) {
        return orderRepository.findByPaymentMethod(paymentMethod).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<ResOrderDTO> getOrdersByShippingMethod(Long shippingMethodId) {
        return orderRepository.findByShippingMethodId(shippingMethodId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<ResOrderDTO> getOrdersByCustomerInfo(String customerName, String customerPhone) {
        if (customerName != null && customerPhone != null) {
            return orderRepository.findByCustomerNameContainingAndCustomerPhone(customerName, customerPhone).stream()
                    .map(orderMapper::toDTO)
                    .toList();
        } else if (customerName != null) {
            return orderRepository.findByCustomerNameContaining(customerName).stream()
                    .map(orderMapper::toDTO)
                    .toList();
        } else if (customerPhone != null) {
            return orderRepository.findByCustomerPhone(customerPhone).stream()
                    .map(orderMapper::toDTO)
                    .toList();
        }
        throw new InvalidException("Vui lòng cung cấp tên hoặc số điện thoại khách hàng");
    }

    public List<ResOrderDTO> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return orderRepository.findByTotalAmountBetween(minAmount, maxAmount).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Transactional
    public ResOrderDTO updateOrderTracking(Long id, String trackingNumber) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getStatus() != Order.OrderStatus.SHIPPED) {
            throw new InvalidException("Chỉ có thể cập nhật tracking cho đơn hàng đã giao");
        }

        order.setTrackingNumber(trackingNumber);
        order.setUpdatedAt(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public ResOrderDTO updateOrderDeliveryDate(Long id, LocalDate estimatedDeliveryDate) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getStatus() != Order.OrderStatus.SHIPPED) {
            throw new InvalidException("Chỉ có thể cập nhật ngày giao hàng cho đơn hàng đã giao");
        }

        order.setEstimatedDeliveryDate(estimatedDeliveryDate);
        order.setUpdatedAt(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    private void validateStatusTransition(Order.OrderStatus currentStatus, Order.OrderStatus newStatus) {
        switch (currentStatus) {
            case PENDING:
                if (newStatus != Order.OrderStatus.PROCESSING && newStatus != Order.OrderStatus.CANCELLED) {
                    throw new InvalidException("Trạng thái không hợp lệ");
                }
                break;
            case PROCESSING:
                if (newStatus != Order.OrderStatus.SHIPPED) {
                    throw new InvalidException("Trạng thái không hợp lệ");
                }
                break;
            case SHIPPED:
                if (newStatus != Order.OrderStatus.DELIVERED) {
                    throw new InvalidException("Trạng thái không hợp lệ");
                }
                break;
            case DELIVERED:
            case CANCELLED:
                throw new InvalidException("Không thể thay đổi trạng thái của đơn hàng đã hoàn thành hoặc đã hủy");
        }
    }

    @Transactional
    public ResOrderDTO processPayment(Long orderId, PaymentRequestDTO paymentRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getPaymentStatus() == Order.PaymentStatus.PAID) {
            throw new InvalidException("Đơn hàng đã được thanh toán");
        }

        // Xử lý thanh toán (giả lập)
        try {
            // TODO: Tích hợp với cổng thanh toán thực tế
            order.setPaymentStatus(Order.PaymentStatus.PAID);
            order.setStatus(Order.OrderStatus.PROCESSING);

            // Tạo giao dịch thanh toán
            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setOrder(order);
            transaction.setAmount(order.getTotalAmount());
            transaction.setPaymentMethod(paymentRequest.getPaymentMethod());
            transaction.setTransactionId(UUID.randomUUID().toString());
            transaction.setStatus(PaymentTransaction.Status.SUCCESS);

            order.getTransactions().add(transaction);
            Order updatedOrder = orderRepository.save(order);

            return orderMapper.toDTO(updatedOrder);
        } catch (Exception e) {
            throw new InvalidException("Thanh toán thất bại: " + e.getMessage());
        }
    }

    @Transactional
    public ResOrderDTO updateShippingStatus(Long orderId, String trackingNumber, Order.ShippingStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new InvalidException("Không thể cập nhật trạng thái vận chuyển cho đơn hàng đã hủy");
        }

        order.setTrackingNumber(trackingNumber);

        // Cập nhật trạng thái đơn hàng dựa trên trạng thái vận chuyển
        switch (status) {
            case SHIPPED:
                order.setStatus(Order.OrderStatus.SHIPPED);
                break;
            case DELIVERED:
                order.setStatus(Order.OrderStatus.DELIVERED);
                break;
            case RETURNED:
                order.setStatus(Order.OrderStatus.CANCELLED);
                break;
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public ResOrderDTO applyCoupon(Long orderId, String couponCode) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy đơn hàng"));

        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new InvalidException("Chỉ có thể áp dụng mã giảm giá cho đơn hàng chưa xử lý");
        }

        // TODO: Tích hợp với service xử lý mã giảm giá
        // Giả lập xử lý mã giảm giá
        BigDecimal discountAmount = new BigDecimal("100000"); // Giảm 100k
        order.setCouponCode(couponCode);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(order.getTotalAmount().subtract(discountAmount));

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    public BigDecimal calculateShippingFee(Long shippingMethodId, String address) {
        ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy phương thức vận chuyển"));

        // TODO: Tích hợp với service tính phí vận chuyển thực tế
        // Giả lập tính phí vận chuyển
        BigDecimal baseFee = shippingMethod.getBaseFee();
        // Có thể thêm logic tính phí dựa trên địa chỉ, khoảng cách, v.v.

        return baseFee;
    }
}
