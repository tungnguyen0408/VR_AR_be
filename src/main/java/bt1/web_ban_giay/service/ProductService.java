package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.ProductAttributeDTO;
import bt1.web_ban_giay.dto.request.ReqProductDTO;
import bt1.web_ban_giay.dto.response.MetaDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import bt1.web_ban_giay.dto.response.ResPageDTO;
import bt1.web_ban_giay.entity.*;
import bt1.web_ban_giay.exception.InvalidException;
import bt1.web_ban_giay.mapper.ProductMapper;
import bt1.web_ban_giay.repository.*;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;

    private String toSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // bỏ dấu tiếng Việt
        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // bỏ ký tự đặc bisệt
                .replaceAll("\\s+", "-")        // thay khoảng trắng bằng dấu -
                .replaceAll("-{2,}", "-")       // gộp dấu - liên tiếp
                .replaceAll("^-|-$", "");       // bỏ dấu - đầu/cuối nếu có
        return slug;
    }

    @Transactional
    public ProductDTO createProduct(ReqProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new InvalidException("Sản phẩm đã tồn tại");
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy danh mục"));

        // Chuyển đổi productDTO thành Product entity
        Product product = productMapper.toEntity(productDTO);
        product.setCategory(category);
        product.setStatus(Product.ProductStatus.ACTIVE);
        product.setSlug(toSlug(productDTO.getName()));

        // Lưu sản phẩm chính vào DB
        Product savedProduct = productRepository.save(product);

        // Lưu hình ảnh sản phẩm (nếu có)
        if (productDTO.getImageUrls() != null) {
            for (String imageUrl : productDTO.getImageUrls()) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(savedProduct);
                productImage.setImageUrl(imageUrl);
                productImageRepository.save(productImage);
            }
        }

        // Lưu các biến thể của sản phẩm (nếu có)
        if (productDTO.getVariants() != null) {
            for (ProductVariantDTO variantDTO : productDTO.getVariants()) {
                ProductVariant variant = new ProductVariant();
                variant.setProduct(savedProduct);  // Liên kết với sản phẩm đã lưu
                variant.setSize(variantDTO.getSize());
                variant.setColor(variantDTO.getColor());
                variant.setSku(variantDTO.getSku());
                variant.setStockQuantity(variantDTO.getStockQuantity());

                // Lưu biến thể vào DB
                productVariantRepository.save(variant);
            }
        }

        // Lưu các thuộc tính của sản phẩm (nếu có)
        if (productDTO.getAttributes() != null) {
            for (ProductAttributeDTO attributeDTO : productDTO.getAttributes()) {
                ProductAttribute attribute = new ProductAttribute();
                attribute.setProduct(savedProduct);
                attribute.setName(attributeDTO.getName());
                attribute.setValue(attributeDTO.getValue());

                // Lưu thuộc tính vào DB
                productAttributeRepository.save(attribute);
            }
        }

        return productMapper.toDTO(savedProduct);
    }

    public ResPageDTO getAllProducts(String filter, Pageable pageable) {
        Specification<Product> spec = null;

        if (filter != null && !filter.trim().isEmpty()) {
            spec = filterSpecificationConverter.convert(filter);
        }

        Page<Product> productPage = (spec != null)
                ? productRepository.findAll(spec, pageable)
                : productRepository.findAll(pageable);

        ResPageDTO res = new ResPageDTO();
        MetaDTO meta = new MetaDTO();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(productPage.getTotalPages());
        meta.setTotal(productPage.getTotalElements());
        res.setMeta(meta);
        res.setResult(productMapper.toDTOList(productPage.getContent()));
        return res;
    }


    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> searchProducts(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new InvalidException("Không tìm thấy danh mục");
        }
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getNewestProducts() {
        List<Product> products = productRepository.findTop10ByOrderByCreatedAtDesc();
        return productMapper.toDTOList(products);
    }

    public ResPageDTO getProductsByGender(String gender, String filter, Pageable pageable) {
        Specification<Product> spec = null;

        // Thêm điều kiện lọc theo giới tính
        if (gender != null && !gender.trim().isEmpty()) {
            spec = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("gender"), gender)
            );
        }

        // Thêm điều kiện lọc khác (nếu có) từ filter
        if (filter != null && !filter.trim().isEmpty()) {
            spec = spec != null ? spec.and(filterSpecificationConverter.convert(filter)) : filterSpecificationConverter.convert(filter);
        }

        Page<Product> productPage = (spec != null)
                ? productRepository.findAll(spec, pageable)
                : productRepository.findAll(pageable);

        // Tạo ResPageDTO để trả về thông tin phân trang
        ResPageDTO res = new ResPageDTO();
        MetaDTO meta = new MetaDTO();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(productPage.getTotalPages());
        meta.setTotal(productPage.getTotalElements());
        res.setMeta(meta);
        res.setResult(productMapper.toDTOList(productPage.getContent()));
        return res;
    }

    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByCategoryAndGender(Long categoryId, String gender) {
        List<Product> products = productRepository.findByCategoryIdAndGender(categoryId, gender);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByCategoryAndPriceRange(Long categoryId, BigDecimal minPrice,
            BigDecimal maxPrice) {
        List<Product> products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByGenderAndPriceRange(String gender, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByGenderAndPriceBetween(gender, minPrice, maxPrice);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByStatus(Product.ProductStatus status) {
        List<Product> products = productRepository.findByStatus(status);
        return productMapper.toDTOList(products);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ReqProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));

        if (!existingProduct.getName().equals(productDTO.getName()) &&
                productRepository.existsByName(productDTO.getName())) {
            throw new InvalidException("Tên sản phẩm đã tồn tại");
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy danh mục"));

        productMapper.updateEntityFromDto(productDTO, existingProduct);
        existingProduct.setCategory(category);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new InvalidException("Không tìm thấy sản phẩm");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDTO updateProductStatus(Long id, Product.ProductStatus status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));
        product.setStatus(status);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }
}
