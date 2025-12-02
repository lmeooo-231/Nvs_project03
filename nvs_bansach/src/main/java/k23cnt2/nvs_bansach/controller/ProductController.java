package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.entity.Product; // Import Entity Product
import k23cnt2.nvs_bansach.service.ProductService;
import org.springframework.web.bind.annotation.*; // Import tất cả các annotation cần thiết
import java.util.List;

@RestController // 🚨 SỬA LỖI: Sử dụng @RestController để trả về JSON
@RequestMapping("/api/products") // Định nghĩa mapping cơ sở
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. Endpoint lấy TẤT CẢ sản phẩm: GET /api/products
    @GetMapping
    public List<Product> listProducts() {
        // Trả về List<Product> (Spring sẽ tự động chuyển thành JSON)
        return productService.findAllProducts();
    }

    // 2. Endpoint lấy sản phẩm theo ID: GET /api/products/{id}
    // 🚨 ĐÂY LÀ PHƯƠNG THỨC BỊ THIẾU GÂY LỖI 404
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        // Yêu cầu ProductService có phương thức findById
        return productService.findById(id);
    }
}