package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.entity.Category; // Yêu cầu Entity Category
import k23cnt2.nvs_bansach.service.CategoryService; // Yêu cầu CategoryService
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories") // Ánh xạ cơ sở: /api/categories
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Lấy danh sách TẤT CẢ danh mục
     * GET /api/categories
     */
    @GetMapping
    public List<Category> listCategories() {
        return categoryService.findAllCategories();
    }

    /**
     * Lấy chi tiết danh mục theo ID
     * GET /api/categories/1
     */
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }
}