package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);

    Optional<ProductVariant> findByProductIdAndSizeAndColor(Long productId, String size, String color);

    Optional<ProductVariant> findByProductIdAndSku(Long productId, String sku);
    boolean existsBySku(String sku);
    Optional<ProductVariant> findBySku(String sku);
    boolean existsBySkuAndIdNot(String sku, Long id);
    List<ProductVariant> findByProductIdAndStockQuantityGreaterThan(Long productId, Integer quantity);
}