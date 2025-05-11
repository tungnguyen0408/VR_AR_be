package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);

//    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);

    void deleteByOrderId(Long orderId);
}