package bt1.web_ban_giay.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String brand;

    @Column(unique = true)
    private String sku;

    private BigDecimal weight;
    private String dimensions;
    private String material;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "is_new")
    private Boolean isNew = true;

    @Column(name = "is_bestseller")
    private Boolean isBestseller = false;

    @Column(name = "rating_average")
    private BigDecimal ratingAverage = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description")
    private String metaDescription;

    @Column(name = "meta_keywords")
    private String metaKeywords;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "product_tag_relations", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<ProductTag> tags = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum ProductStatus {
        ACTIVE, INACTIVE
    }

    public enum Gender {
        MALE,
        FEMALE,
        UNISEX;

        public static Gender fromString(String gender) {
            switch (gender.toUpperCase()) {
                case "MALE":
                    return MALE;
                case "FEMALE":
                    return FEMALE;
                case "UNISEX":
                    return UNISEX;
                default:
                    throw new IllegalArgumentException("Invalid gender: " + gender);
            }
        }
    }

}