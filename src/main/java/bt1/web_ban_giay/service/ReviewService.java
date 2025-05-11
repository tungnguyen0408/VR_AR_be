package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.ReviewDTO;
import bt1.web_ban_giay.entity.Order;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.entity.Review;
import bt1.web_ban_giay.entity.User;
import bt1.web_ban_giay.mapper.ReviewMapper;
import bt1.web_ban_giay.repository.OrderRepository;
import bt1.web_ban_giay.repository.ProductRepository;
import bt1.web_ban_giay.repository.ReviewRepository;
import bt1.web_ban_giay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Validate user exists
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate product exists
        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validate order exists and belongs to user
        Order order = orderRepository.findById(reviewDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Order does not belong to user");
        }

        // Check if user has already reviewed this product
        if (reviewRepository.existsByUserAndProduct(user, product)) {
            throw new RuntimeException("User has already reviewed this product");
        }

        // Create review
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setUser(user);
        review.setProduct(product);
        review.setOrder(order);

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByProduct(product).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reviewRepository.findByUser(user).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}