package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Phương thức tùy chỉnh: Tìm tất cả Review cho một Product
    List<Review> findByProductId(Long productId);

    // Phương thức tùy chỉnh: Tìm tất cả Review của một User
    List<Review> findByUserId(Long userId);
}