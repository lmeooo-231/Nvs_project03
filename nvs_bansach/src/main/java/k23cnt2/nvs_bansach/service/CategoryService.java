package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.entity.Category;
import k23cnt2.nvs_bansach.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Lấy danh sách TẤT CẢ danh mục.
     * Dùng cho GET /api/categories
     */
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Tìm danh mục theo ID.
     * Dùng cho GET /api/categories/{id}
     */
    public Category findById(Long id) {
        // Ném ngoại lệ nếu không tìm thấy
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    /**
     * Tạo một danh mục mới.
     * Dùng cho POST /api/categories
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}