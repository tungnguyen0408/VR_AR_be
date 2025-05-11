package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
        boolean existsByName(String name);

        // Tìm kiếm sản phẩm theo tên
        List<Product> findByNameContainingIgnoreCase(String name);

        // Lọc sản phẩm theo danh mục
        List<Product> findByCategoryId(Long categoryId);

        // Lấy 10 sản phẩm mới nhất
        List<Product> findTop10ByOrderByCreatedAtDesc();

        // Lọc sản phẩm theo giới tính
        Page<Product> findByGender(String gender, Pageable pageable);

        // Lấy sản phẩm theo khoảng giá
        List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

        // Lấy sản phẩm theo danh mục và giới tính
        List<Product> findByCategoryIdAndGender(Long categoryId, String gender);

        // Lấy sản phẩm theo danh mục và khoảng giá
        List<Product> findByCategoryIdAndPriceBetween(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice);

        // Lấy sản phẩm theo giới tính và khoảng giá
        List<Product> findByGenderAndPriceBetween(String gender, BigDecimal minPrice, BigDecimal maxPrice);

        // Lấy sản phẩm theo trạng thái
        List<Product> findByStatus(Product.ProductStatus status);

        // Lấy sản phẩm theo danh mục và trạng thái
        List<Product> findByCategoryIdAndStatus(Long categoryId, Product.ProductStatus status);

        // Lấy sản phẩm theo giới tính và trạng thái
        List<Product> findByGenderAndStatus(String gender, Product.ProductStatus status);

        // Lấy sản phẩm theo khoảng giá và trạng thái
        List<Product> findByPriceBetweenAndStatus(BigDecimal minPrice, BigDecimal maxPrice,
                        Product.ProductStatus status);

        // Lấy sản phẩm theo danh mục, giới tính và trạng thái
        List<Product> findByCategoryIdAndGenderAndStatus(Long categoryId, String gender, Product.ProductStatus status);

        // Lấy sản phẩm theo danh mục, khoảng giá và trạng thái
        List<Product> findByCategoryIdAndPriceBetweenAndStatus(Long categoryId, BigDecimal minPrice,
                        BigDecimal maxPrice, Product.ProductStatus status);

        // Lấy sản phẩm theo giới tính, khoảng giá và trạng thái
        List<Product> findByGenderAndPriceBetweenAndStatus(String gender, BigDecimal minPrice, BigDecimal maxPrice,
                        Product.ProductStatus status);
}
