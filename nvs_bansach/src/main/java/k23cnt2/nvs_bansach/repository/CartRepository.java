package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.CartItem;
import k23cnt2.nvs_bansach.entity.Product;
import k23cnt2.nvs_bansach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    /**
     * Tìm kiếm tất cả các CartItem (Mục trong giỏ hàng) thuộc về một User cụ thể.
     * Được sử dụng trong CartService (khi xem giỏ hàng) và OrderService (khi checkout).
     *
     * @param user Đối tượng User
     * @return Danh sách các CartItem
     */
    List<CartItem> findByUser(User user);

    /**
     * (Tùy chọn cho logic Giỏ hàng) Tìm kiếm một CartItem cụ thể dựa trên User và Product.
     * Hữu ích khi người dùng thêm cùng một sản phẩm vào giỏ hàng nhiều lần.
     *
     * @param user Đối tượng User
     * @param product Đối tượng Product
     * @return Optional<CartItem> (có thể rỗng nếu chưa có)
     */
    CartItem findByUserAndProduct(User user, Product product);

    // Nếu bạn không có Product entity trong package, bạn cần import nó.
    // import k23cnt2.nvs_bansach.entity.Product;
}