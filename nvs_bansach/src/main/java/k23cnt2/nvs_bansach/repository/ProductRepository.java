package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Đánh dấu đây là một Repository của Spring Data JPA
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Không cần code thêm, JpaRepository đã cung cấp CRUD
}