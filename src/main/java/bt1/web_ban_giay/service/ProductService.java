package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.request.ProductFilterDTO;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

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
    private CategoryRepository categoryRepository;
    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;

    private String toSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // bỏ dấu tiếng Việt
        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // bỏ ký tự đặc bisệt
                .replaceAll("\\s+", "-") // thay khoảng trắng bằng dấu -
                .replaceAll("-{2,}", "-") // gộp dấu - liên tiếp
                .replaceAll("^-|-$", ""); // bỏ dấu - đầu/cuối nếu có
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
            List<ProductImage> productImages = new ArrayList<>();
            for (String imageUrl : productDTO.getImageUrls()) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(savedProduct);
                productImage.setImageUrl(imageUrl);
                productImageRepository.save(productImage);
                productImages.add(productImage);
            }
            savedProduct.setImages(productImages); // 🟢 Gán lại cho entity
        }

        // Lưu các biến thể của sản phẩm (nếu có)
        if (productDTO.getVariants() != null) {
            List<ProductVariant> productVariants = new ArrayList<>();
            for (ProductVariantDTO variantDTO : productDTO.getVariants()) {
                ProductVariant variant = new ProductVariant();
                variant.setProduct(savedProduct);
                variant.setSize(variantDTO.getSize());
                variant.setColor(variantDTO.getColor());
                variant.setSku(variantDTO.getSku());
                variant.setStockQuantity(variantDTO.getStockQuantity());
                productVariantRepository.save(variant);
                productVariants.add(variant);
            }
            savedProduct.setVariants(productVariants);
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

    public ResPageDTO searchProducts(String keyword, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        ResPageDTO res = new ResPageDTO();
        MetaDTO meta = new MetaDTO();
        meta.setPage(productPage.getNumber() + 1);
        meta.setPageSize(productPage.getSize());
        meta.setPages(productPage.getTotalPages());
        meta.setTotal(productPage.getTotalElements());
        res.setMeta(meta);
        res.setResult(productMapper.toDTOList(productPage.getContent()));
        return res;
    }

    public ResPageDTO getProductsByCategory(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new InvalidException("Không tìm thấy danh mục");
        }
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        return buildResPageDTO(productPage);
    }

    public ResPageDTO getNewestProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return buildResPageDTO(products);
    }

    public ResPageDTO getProductsByGender(String gender, String filter, Pageable pageable) {
        Specification<Product> spec = null;

        // Thêm điều kiện lọc theo giới tính
        if (gender != null && !gender.trim().isEmpty()) {
            spec = Specification
                    .where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"), gender));
        }

        // Thêm điều kiện lọc khác (nếu có) từ filter
        if (filter != null && !filter.trim().isEmpty()) {
            spec = spec != null ? spec.and(filterSpecificationConverter.convert(filter))
                    : filterSpecificationConverter.convert(filter);
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

    public ResPageDTO getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Page<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        return buildResPageDTO(products);
    }

    public ResPageDTO getProductsByCategoryAndGender(Long categoryId, String gender, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryIdAndGender(categoryId, gender, pageable);
        return buildResPageDTO(products);
    }

    public ResPageDTO getProductsByCategoryAndPriceRange(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
            Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice,
                pageable);
        return buildResPageDTO(products);
    }

    public ResPageDTO getProductsByGenderAndPriceRange(String gender, BigDecimal minPrice, BigDecimal maxPrice,
            Pageable pageable) {
        Page<Product> products = productRepository.findByGenderAndPriceBetween(gender, minPrice, maxPrice, pageable);
        return buildResPageDTO(products);
    }

    public ResPageDTO getProductsByStatus(Product.ProductStatus status, Pageable pageable) {
        Page<Product> products = productRepository.findByStatus(status, pageable);
        return buildResPageDTO(products);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ReqProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));

        // Kiểm tra trùng tên sản phẩm
        if (!existingProduct.getName().equals(productDTO.getName()) &&
                productRepository.existsByName(productDTO.getName())) {
            throw new InvalidException("Tên sản phẩm đã tồn tại");
        }

        // Cập nhật category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy danh mục"));
        productMapper.updateEntityFromDto(productDTO, existingProduct);
        existingProduct.setCategory(category);
        // --- Cập nhật variants ---
        List<ProductVariant> variantsInDB = new ArrayList<>(existingProduct.getVariants());
        Map<Long, ProductVariant> existingVariantMap = variantsInDB.stream()
                .filter(v -> v.getId() != null)
                .collect(Collectors.toMap(ProductVariant::getId, v -> v));

        Set<Long> idsFromRequest = new HashSet<>();
        Set<String> skusInRequest = new HashSet<>();

        List<ProductVariant> variantsToAddOrUpdate = new ArrayList<>();

        if (productDTO.getVariants() != null) {
            for (ProductVariantDTO variantDTO : productDTO.getVariants()) {
                if (!skusInRequest.add(variantDTO.getSku())) {
                    throw new InvalidException("SKU bị trùng trong danh sách: " + variantDTO.getSku());
                }

                ProductVariant variant = null;

                if (variantDTO.getId() != null && existingVariantMap.containsKey(variantDTO.getId())) {
                    // Cập nhật variant theo ID
                    variant = existingVariantMap.get(variantDTO.getId());

                    if (!Objects.equals(variant.getSku(), variantDTO.getSku())) {
                        if (productVariantRepository.existsBySkuAndIdNot(variantDTO.getSku(), variantDTO.getId())) {
                            throw new InvalidException("SKU đã tồn tại: " + variantDTO.getSku());
                        }
                        variant.setSku(variantDTO.getSku());
                    }

                    variant.setSize(variantDTO.getSize());
                    variant.setColor(variantDTO.getColor());
                    variant.setStockQuantity(variantDTO.getStockQuantity());
                    variant.setProduct(existingProduct);

                    idsFromRequest.add(variantDTO.getId());
                } else {
                    // Kiểm tra nếu SKU đã tồn tại trong DB → cập nhật variant đó (không thay đổi
                    // SKU)
                    Optional<ProductVariant> existingBySku = productVariantRepository.findBySku(variantDTO.getSku());
                    if (existingBySku.isPresent()) {
                        variant = existingBySku.get();
                        variant.setSize(variantDTO.getSize());
                        variant.setColor(variantDTO.getColor());
                        variant.setStockQuantity(variantDTO.getStockQuantity());
                        variant.setProduct(existingProduct);
                    } else {
                        // SKU chưa tồn tại → tạo mới variant
                        variant = new ProductVariant();
                        variant.setSku(variantDTO.getSku());
                        variant.setSize(variantDTO.getSize());
                        variant.setColor(variantDTO.getColor());
                        variant.setStockQuantity(variantDTO.getStockQuantity());
                        variant.setProduct(existingProduct);
                    }
                }

                variantsToAddOrUpdate.add(variant);
            }
        }

        // Xóa các variant không còn trong request
        existingProduct.getVariants().removeIf(v -> v.getId() != null && !idsFromRequest.contains(v.getId()));

        // Thêm/ cập nhật các variant mới hoặc chỉnh sửa
        for (ProductVariant v : variantsToAddOrUpdate) {
            if (!existingProduct.getVariants().contains(v)) {
                existingProduct.getVariants().add(v);
            }
        }

        // --- Cập nhật hình ảnh ---
        existingProduct.getImages().clear();
        if (productDTO.getImageUrls() != null) {
            for (String url : productDTO.getImageUrls()) {
                ProductImage img = new ProductImage();
                img.setImageUrl(url);
                img.setProduct(existingProduct);
                existingProduct.getImages().add(img);
            }
        }

        // Lưu product (các changes với variants sẽ cascade)
        Product updated = productRepository.save(existingProduct);

        return productMapper.toDTO(updated);
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
        Product updatedProduct = this.productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    public ResPageDTO filterProducts(ProductFilterDTO filterDTO, Pageable pageable) {
        StringBuilder filterBuilder = new StringBuilder();

        // Build filter string for turkraft.springfilter
        if (filterDTO.getBrands() != null && !filterDTO.getBrands().isEmpty()) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("brand=in=(");
            filterBuilder.append(String.join(",", filterDTO.getBrands()));
            filterBuilder.append(")");
        }

        if (filterDTO.getMinPrice() != null) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("price>==").append(filterDTO.getMinPrice());
        }

        if (filterDTO.getMaxPrice() != null) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("price<==").append(filterDTO.getMaxPrice());
        }

        if (filterDTO.getColors() != null && !filterDTO.getColors().isEmpty()) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("variants.color=in=(");
            filterBuilder.append(String.join(",", filterDTO.getColors()));
            filterBuilder.append(")");
        }

        if (filterDTO.getSizes() != null && !filterDTO.getSizes().isEmpty()) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("variants.size=in=(");
            filterBuilder.append(String.join(",", filterDTO.getSizes().stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList())));
            filterBuilder.append(")");
        }

        if (filterDTO.getGender() != null && !filterDTO.getGender().isEmpty()) {
            if (filterBuilder.length() > 0)
                filterBuilder.append(";");
            filterBuilder.append("gender==").append(filterDTO.getGender());
        }

        // Apply sorting if specified
        if (filterDTO.getSortBy() != null && !filterDTO.getSortBy().isEmpty()) {
            String sortDirection = filterDTO.getSortDirection() != null ? filterDTO.getSortDirection().toUpperCase()
                    : "ASC";
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), filterDTO.getSortBy());
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        String filterString = filterBuilder.toString();
        Specification<Product> spec = filterString.isEmpty() ? null
                : filterSpecificationConverter.convert(filterString);

        Page<Product> productPage = (spec != null) ? productRepository.findAll(spec, pageable)
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

    public ResPageDTO getBestsellerProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByIsBestsellerTrue(pageable);
        return buildResPageDTO(productPage);
    }

    public ResPageDTO getDiscountedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findDiscountedProducts(pageable);

        return buildResPageDTO(productPage);
    }



    private ResPageDTO buildResPageDTO(Page<Product> productPage) {
        ResPageDTO res = new ResPageDTO();
        MetaDTO meta = new MetaDTO();
        meta.setPage(productPage.getNumber() + 1);
        meta.setPageSize(productPage.getSize());
        meta.setPages(productPage.getTotalPages());
        meta.setTotal(productPage.getTotalElements());
        res.setMeta(meta);
        res.setResult(productMapper.toDTOList(productPage.getContent()));
        return res;
    }
}
