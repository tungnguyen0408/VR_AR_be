package bt1.web_ban_giay.service.impl;

import bt1.web_ban_giay.dto.request.ReviewDTO;
import bt1.web_ban_giay.dto.response.ReviewResponseDTO;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.entity.Review;
import bt1.web_ban_giay.entity.User;
import bt1.web_ban_giay.exception.ResourceNotFoundException;
import bt1.web_ban_giay.mapper.ReviewMapper;
import bt1.web_ban_giay.repository.ProductRepository;
import bt1.web_ban_giay.repository.ReviewRepository;
import bt1.web_ban_giay.repository.UserRepository;
import bt1.web_ban_giay.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ReviewResponseDTO createReview(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (reviewRepository.existsByUserAndProduct(user, product)) {
            throw new IllegalArgumentException("User has already reviewed this product");
        }

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setUser(user);
        review.setProduct(product);

        Review savedReview = reviewRepository.save(review);

        // Update product's average rating
        Double averageRating = reviewRepository.getAverageRatingByProduct(product);
        if (averageRating != null) {
            product.setRatingAverage(BigDecimal.valueOf(averageRating).setScale(1, RoundingMode.HALF_UP));
            productRepository.save(product);
        }

        return reviewMapper.toDTO(savedReview);
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepository.save(review);

        // Update product's average rating
        Product product = review.getProduct();
        Double averageRating = reviewRepository.getAverageRatingByProduct(product);
        if (averageRating != null) {
            product.setRatingAverage(BigDecimal.valueOf(averageRating).setScale(1, RoundingMode.HALF_UP));
            productRepository.save(product);
        }

        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        Product product = review.getProduct();
        reviewRepository.deleteById(id);

        // Update product's average rating
        Double averageRating = reviewRepository.getAverageRatingByProduct(product);
        if (averageRating != null) {
            product.setRatingAverage(BigDecimal.valueOf(averageRating).setScale(1, RoundingMode.HALF_UP));
            productRepository.save(product);
        }
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return reviewMapper.toDTO(review);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return reviewMapper.toDTOList(reviewRepository.findByProduct(product));
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return reviewMapper.toDTOList(reviewRepository.findByUser(user));
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewMapper.toDTOList(reviewRepository.findAll());
    }

    @Override
    public double getAverageRatingByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Double averageRating = reviewRepository.getAverageRatingByProduct(product);
        return averageRating != null ? averageRating : 0.0;
    }
}