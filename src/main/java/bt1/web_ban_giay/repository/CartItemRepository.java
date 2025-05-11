package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductIdAndProductVariantId(Long cartId, Long productId, Long variantId);

    int countByCartUserId(Long userId);

    void deleteByCartId(Long cartId);
}