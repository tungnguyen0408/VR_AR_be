package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    // Các phương thức tìm kiếm tùy chỉnh có thể được thêm ở đây nếu cần
}