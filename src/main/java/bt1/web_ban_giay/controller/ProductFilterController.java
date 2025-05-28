package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.ProductFilterDTO;
import bt1.web_ban_giay.dto.response.ResPageDTO;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filter")
@CrossOrigin(origins = "*")
public class ProductFilterController {

    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<ResPageDTO> filterProducts(
            @RequestBody ProductFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        ResPageDTO result = productService.filterProducts(filterDTO, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAvailableBrands(
            @RequestParam(required = false) Product.Gender gender) {
        List<String> brands = productService.getAvailableBrands(gender);
        return ResponseEntity.ok(brands);
    }

    // Các endpoint khác giữ nguyên
    @GetMapping("/sizes")
    public ResponseEntity<List<String>> getAvailableSizes(
            @RequestParam(required = false) Product.Gender gender) {
        List<String> sizes = productService.getAvailableSizes(gender);
        return ResponseEntity.ok(sizes);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAvailableColors(
            @RequestParam(required = false) Product.Gender gender) {
        List<String> colors = productService.getAvailableColors(gender);
        return ResponseEntity.ok(colors);
    }
}