package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.CartItem;
import k23cnt2.nvs_bansach.entity.OrderItem;
import k23cnt2.nvs_bansach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Phương thức tùy chỉnh: Tìm tất cả OrderItem của một Order cụ thể
    List<OrderItem> findByOrderId(Long orderId);

    // Bạn có thể thêm các phương thức tìm kiếm phức tạp hơn nếu cần
}