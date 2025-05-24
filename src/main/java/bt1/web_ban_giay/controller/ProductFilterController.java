package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.ProductFilterDTO;
import bt1.web_ban_giay.dto.response.ResPageDTO;
import bt1.web_ban_giay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/products/filter")
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
    }