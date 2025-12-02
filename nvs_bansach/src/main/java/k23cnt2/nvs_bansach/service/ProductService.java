package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.entity.Product;
import k23cnt2.nvs_bansach.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // BẮT BUỘC: Đánh dấu đây là Service Bean của Spring
public class ProductService {

    private final ProductRepository productRepository;

    // Tiêm ProductRepository vào Service
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Phương thức 1: Lấy tất cả sản phẩm (cho GET /api/products)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 🚨 PHƯƠNG THỨC BỔ SUNG: Tìm sản phẩm theo ID (cho GET /api/products/{id})
    public Product findById(Long id) {
        // Sử dụng findById của JPA và ném ngoại lệ nếu không tìm thấy
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    // (Tùy chọn) Thêm các phương thức khác như createProduct, updateProduct, deleteProduct...
}