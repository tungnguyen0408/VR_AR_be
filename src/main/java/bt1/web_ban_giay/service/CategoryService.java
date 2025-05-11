package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    CategoryDTO getCategoryById(Long id);

    CategoryDTO getCategoryBySlug(String slug);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getChildCategories(Long parentId);

    List<CategoryDTO> getRootCategories();
}