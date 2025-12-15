package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.entity.*;
import k23cnt2.nvs_bansach.entity.Order.OrderStatus; // Import đúng OrderStatus
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
        // ... (Logic Checkout - Giữ nguyên như bạn đã gửi)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng của người dùng ID " + userId + " đang rỗng. Không thể đặt hàng.");
        }

        double total = 0;
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getStock() < quantity) {
                throw new RuntimeException("Sản phẩm '" + product.getTitle() + "' không đủ tồn kho.");
            }

            total += product.getPrice() * quantity;
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotal(total);
        order.setStatus(Order.OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }

        cartRepository.deleteAll(cartItems);
        return order;
    }

    // =========================================================================
    // 2. PHƯƠNG THỨC HỖ TRỢ
    // =========================================================================

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    /**
     * Lấy lịch sử đơn hàng của người dùng.
     * 🚨 Sửa lỗi: Phương thức này đã được đưa vào trong phạm vi của class OrderService.
     */
    public List<Order> findOrdersByUserId(Long userId) {
        // 1. Tìm User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 2. Gọi Repository để lấy danh sách đơn hàng của User đó
        // Yêu cầu phương thức findByUser trong OrderRepository
        return orderRepository.findByUser(user);
    }
}