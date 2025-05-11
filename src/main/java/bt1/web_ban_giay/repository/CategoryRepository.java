package bt1.web_ban_giay.repository;

import bt1.web_ban_giay.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);

    List<Category> findByParentId(Long parentId);

    List<Category> findByParentIsNull();

    boolean existsBySlug(String slug);

    boolean existsByName(String name);
}
