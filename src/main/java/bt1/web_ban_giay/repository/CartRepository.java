package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    // Lấy số lượng sản phẩm trong giỏ hàng
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.user.id = :userId")
    int countCartItemsByUserId(Long userId);

    // Lấy giỏ hàng của user
    Optional<Cart> findByUserId(Long userId);

    // Xóa toàn bộ giỏ hàng của user
    void deleteByUserId(Long userId);

    // Lấy danh sách giỏ hàng theo điều kiện
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findAllByUserId(Long userId);

    // Lấy giỏ hàng theo ID và user ID
    @Query("SELECT c FROM Cart c WHERE c.id = :cartId AND c.user.id = :userId")
    Optional<Cart> findByIdAndUserId(Long cartId, Long userId);
}
