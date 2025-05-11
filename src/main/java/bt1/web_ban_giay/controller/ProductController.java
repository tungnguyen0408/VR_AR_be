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

    @GetMapping
    public ResponseEntity<ResPageDTO> getAllProducts(@Filter String filter, Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/newest")
    public ResponseEntity<List<ProductDTO>> getNewestProducts() {
        return ResponseEntity.ok(productService.getNewestProducts());
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
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/category/{categoryId}/gender/{gender}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndGender(
            @PathVariable Long categoryId,
            @PathVariable String gender) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndGender(categoryId, gender));
    }

    @GetMapping("/category/{categoryId}/price-range")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndPriceRange(
            @PathVariable Long categoryId,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndPriceRange(categoryId, minPrice, maxPrice));
    }

    @GetMapping("/gender/{gender}/price-range")
    public ResponseEntity<List<ProductDTO>> getProductsByGenderAndPriceRange(
            @PathVariable String gender,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.getProductsByGenderAndPriceRange(gender, minPrice, maxPrice));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable Product.ProductStatus status) {
        return ResponseEntity.ok(productService.getProductsByStatus(status));
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
}
