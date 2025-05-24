package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.ReqProductDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.dto.response.ResPageDTO;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.service.ProductService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ReqProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/all")
    public ResponseEntity<ResPageDTO> getAllProducts(@Filter String filter, Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ResPageDTO> searchProducts(
            @RequestParam String keyword,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResPageDTO response = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResPageDTO> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId, pageable));
    }

    @GetMapping("/newest")
    public ResponseEntity<ResPageDTO> getNewestProducts( @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.getNewestProducts(pageable));
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<ResPageDTO> getProductsByGender(
            @PathVariable String gender,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ResPageDTO response = productService.getProductsByGender(gender, filter, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/price-range")
    public ResponseEntity<ResPageDTO> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResPageDTO response = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}/gender/{gender}")
    public ResponseEntity<ResPageDTO> getProductsByCategoryAndGender(
            @PathVariable Long categoryId,
            @PathVariable String gender,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResPageDTO response = productService.getProductsByCategoryAndGender(categoryId, gender, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}/price-range")
    public ResponseEntity<ResPageDTO> getProductsByCategoryAndPriceRange(
            @PathVariable Long categoryId,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity
                .ok(productService.getProductsByCategoryAndPriceRange(categoryId, minPrice, maxPrice, pageable));
    }

    @GetMapping("/gender/{gender}/price-range")
    public ResponseEntity<ResPageDTO> getProductsByGenderAndPriceRange(
            @PathVariable String gender,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResPageDTO response = productService.getProductsByGenderAndPriceRange(gender, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResPageDTO> getProductsByStatus(
            @PathVariable Product.ProductStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByStatus(status, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ReqProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ProductDTO> updateProductStatus(
            @PathVariable Long id,
            @RequestParam Product.ProductStatus status) {
        return ResponseEntity.ok(productService.updateProductStatus(id, status));
    }

    @GetMapping("/bestsellers")
    public ResPageDTO getBestsellerProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        return productService.getBestsellerProducts(page, size);
    }

    @GetMapping("/discounted")
    public ResponseEntity<ResPageDTO> getDiscountedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        return ResponseEntity.ok(productService.getDiscountedProducts(page, size));
    }


}
