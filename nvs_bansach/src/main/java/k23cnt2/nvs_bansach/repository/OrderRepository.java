package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Phương thức tùy chỉnh: Tìm tất cả Order của một User cụ thể
    List<Order> findByUserId(Long userId);

    // Phương thức tùy chỉnh: Tìm các Order có trạng thái cụ thể
    List<Order> findByStatus(Order.OrderStatus status);
}