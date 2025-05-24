package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.request.ReviewDTO;
import bt1.web_ban_giay.dto.response.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {
        ReviewResponseDTO createReview(ReviewDTO reviewDTO);

        ReviewResponseDTO updateReview(Long id, ReviewDTO reviewDTO);

        void deleteReview(Long id);

        ReviewResponseDTO getReviewById(Long id);

        List<ReviewResponseDTO> getReviewsByProductId(Long productId);

        List<ReviewResponseDTO> getReviewsByUserId(Long userId);

        List<ReviewResponseDTO> getAllReviews();

        double getAverageRatingByProductId(Long productId);
}