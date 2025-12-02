package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.entity.*;
import k23cnt2.nvs_bansach.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        CartRepository cartRepository, UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // =========================================================================
    // 1. CHỨC NĂNG CHECKOUT (ĐẶT HÀNG)
    // =========================================================================

    @Transactional
    public Order checkout(Long userId) {
        // 1. Lấy thông tin User và các CartItem
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 🚨 SỬA LỖI 1: findByUser() phải được định nghĩa trong CartRepository
        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng của người dùng ID " + userId + " đang rỗng. Không thể đặt hàng.");
        }

        // 2. Tính toán tổng tiền và kiểm tra tồn kho
        double total = 0;
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getStock() < quantity) {
                throw new RuntimeException("Sản phẩm '" + product.getTitle() + "' không đủ tồn kho.");
            }

            total += product.getPrice() * quantity;
        }

        // 3. Tạo đối tượng Order
        Order order = new Order();
        order.setUser(user);
        order.setTotal(total);

        // 🚨 SỬA LỖI 2: Sử dụng Enum lồng nhau (nested enum) của Order.java
        // Bạn đã định nghĩa OrderStatus bên trong Order, nên phải gọi: Order.OrderStatus.CREATED
        order.setStatus(Order.OrderStatus.CREATED);

        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        // 4. Tạo OrderItem và giảm tồn kho (Stock)
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);

            // Cập nhật tồn kho
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }

        // 5. Dọn dẹp Giỏ hàng
        // 🚨 SỬA LỖI 3: deleteAll() có thể chấp nhận List
        cartRepository.deleteAll(cartItems);

        // 6. Trả về Order đã hoàn tất
        return order;
    }

    // =========================================================================
    // 2. PHƯƠNG THỨC HỖ TRỢ
    // =========================================================================

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }
}