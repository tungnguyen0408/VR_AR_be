package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.entity.Review;
import bt1.web_ban_giay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);

    List<Review> findByUser(User user);

    boolean existsByUserAndProduct(User user, Product product);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product = ?1")
    Double getAverageRatingByProduct(Product product);
}