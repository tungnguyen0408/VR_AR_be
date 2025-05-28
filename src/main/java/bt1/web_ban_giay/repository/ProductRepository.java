package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
        boolean existsByName(String name);

        // Tìm kiếm sản phẩm theo tên
        Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

        @Query("SELECT p FROM Product p WHERE p.salePrice IS NOT NULL AND p.salePrice < p.price")
        Page<Product> findDiscountedProducts(Pageable pageable);

        Page<Product> findByIsBestsellerTrue(Pageable pageable);

        // Lọc sản phẩm theo danh mục
        Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

        // Lấy 10 sản phẩm mới nhất
        List<Product> findTop10ByOrderByCreatedAtDesc();

        // Lọc sản phẩm theo giới tính
        Page<Product> findByGender(String gender, Pageable pageable);

        // Lấy sản phẩm theo khoảng giá
        Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

        // Lấy sản phẩm theo danh mục và giới tính
        List<Product> findByCategoryIdAndGender(Long categoryId, String gender);

        // Lấy sản phẩm theo danh mục và khoảng giá
        Page<Product> findByCategoryIdAndPriceBetween(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                        Pageable pageable);

        // Lấy sản phẩm theo giới tính và khoảng giá
        Page<Product> findByGenderAndPriceBetween(String gender, BigDecimal minPrice, BigDecimal maxPrice,
                        Pageable pageable);

        // Lấy sản phẩm theo trạng thái
        Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable);

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

        // Lấy sản phẩm theo danh mục và giới tính (có phân trang)
        Page<Product> findByCategoryIdAndGender(Long categoryId, String gender, Pageable pageable);

        // Lấy sản phẩm theo danh mục và trạng thái (có phân trang)
        Page<Product> findByCategoryIdAndStatus(Long categoryId, Product.ProductStatus status, Pageable pageable);

        // Lấy sản phẩm theo giới tính và trạng thái (có phân trang)
        Page<Product> findByGenderAndStatus(String gender, Product.ProductStatus status, Pageable pageable);

        // Lấy sản phẩm theo khoảng giá và trạng thái (có phân trang)
        Page<Product> findByPriceBetweenAndStatus(BigDecimal minPrice, BigDecimal maxPrice,
                        Product.ProductStatus status, Pageable pageable);

        // Lấy sản phẩm theo danh mục, giới tính và trạng thái (có phân trang)
        Page<Product> findByCategoryIdAndGenderAndStatus(Long categoryId, String gender, Product.ProductStatus status,
                        Pageable pageable);

        // Lấy sản phẩm theo danh mục, khoảng giá và trạng thái (có phân trang)
        Page<Product> findByCategoryIdAndPriceBetweenAndStatus(Long categoryId, BigDecimal minPrice,
                        BigDecimal maxPrice, Product.ProductStatus status, Pageable pageable);

        // Lấy sản phẩm theo giới tính, khoảng giá và trạng thái (có phân trang)
        Page<Product> findByGenderAndPriceBetweenAndStatus(String gender, BigDecimal minPrice, BigDecimal maxPrice,
                        Product.ProductStatus status, Pageable pageable);

        @Query("SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN p.variants v " +
                        "WHERE (:brands IS NULL OR p.brand IN :brands) " +
                        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                        "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
                        "AND (:colors IS NULL OR v.color IN :colors) " +
                        "AND (:sizes IS NULL OR v.size IN :sizes) " +
                        "AND (:gender IS NULL OR p.gender = :gender)")
        Page<Product> findProductsWithFilters(
                        @Param("brands") List<String> brands,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        @Param("colors") List<String> colors,
                        @Param("sizes") List<Integer> sizes,
                        @Param("gender") Product.Gender gender,
                        Pageable pageable);



        @Query("SELECT DISTINCT p FROM Product p " +
                "LEFT JOIN p.variants v " +
                "WHERE " +
                "(:brands IS NULL OR p.brand IN :brands) AND " +
                "(:gender IS NULL OR p.gender = :gender) AND " +
                "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
                "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
                "(:isBestseller IS NULL OR p.isBestseller = :isBestseller) AND " +
                "(:isNew IS NULL OR p.isNew = :isNew) AND " +
                "(:isFeatured IS NULL OR p.isFeatured = :isFeatured) AND " +
                "(:sizes IS NULL OR v.size IN :sizes) AND " +
                "(:colors IS NULL OR v.color IN :colors) AND " +
                "p.status = 'ACTIVE'")
        Page<Product> filterProducts(
                @Param("brands") List<String> brands,
                @Param("gender") Product.Gender gender,
                @Param("minPrice") BigDecimal minPrice,
                @Param("maxPrice") BigDecimal maxPrice,
                @Param("isBestseller") Boolean isBestseller,
                @Param("isNew") Boolean isNew,
                @Param("isFeatured") Boolean isFeatured,
                @Param("sizes") List<String> sizes,
                @Param("colors") List<String> colors,
                Pageable pageable
        );
        @Query("SELECT DISTINCT p.brand FROM Product p " +
                "WHERE p.status = 'ACTIVE' " +
                "AND (:gender IS NULL OR p.gender = :gender) " +
                "AND p.brand IS NOT NULL " +
                "ORDER BY p.brand")
        List<String> findAvailableBrands(@Param("gender") Product.Gender gender);

        @Query("SELECT DISTINCT v.size FROM Product p " +
                "JOIN p.variants v " +
                "WHERE p.status = 'ACTIVE' " +
                "AND (:gender IS NULL OR p.gender = :gender) " +
                "AND v.size IS NOT NULL " +
                "AND v.stockQuantity > 0 " +
                "ORDER BY CAST(v.size AS integer)")
        List<String> findAvailableSizes(@Param("gender") Product.Gender gender);

        @Query("SELECT DISTINCT v.color FROM Product p " +
                "JOIN p.variants v " +
                "WHERE p.status = 'ACTIVE' " +
                "AND (:gender IS NULL OR p.gender = :gender) " +
                "AND v.color IS NOT NULL " +
                "AND v.stockQuantity > 0 " +
                "ORDER BY v.color")
        List<String> findAvailableColors(@Param("gender") Product.Gender gender);
}
