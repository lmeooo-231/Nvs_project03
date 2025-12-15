package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.Order;
import k23cnt2.nvs_bansach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 🚨 PHƯƠNG THỨC BỔ SUNG: Tìm tất cả các đơn hàng của một người dùng.
     * Dùng cho chức năng Lịch sử Đơn hàng.
     */
    List<Order> findByUser(User user);

    // (Tùy chọn) Sắp xếp theo ngày tạo giảm dần
    // List<Order> findByUserOrderByCreatedAtDesc(User user);
}