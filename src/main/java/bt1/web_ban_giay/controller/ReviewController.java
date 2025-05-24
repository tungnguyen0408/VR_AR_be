package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.ReviewDTO;
import bt1.web_ban_giay.dto.response.ReviewResponseDTO;
import bt1.web_ban_giay.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRatingByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getAverageRatingByProductId(productId));
    }
}