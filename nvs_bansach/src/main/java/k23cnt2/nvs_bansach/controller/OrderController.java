package k23cnt2.nvs_bansach.controller; // Đảm bảo đúng package

import k23cnt2.nvs_bansach.entity.Order;
import k23cnt2.nvs_bansach.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders") // Ánh xạ cơ sở cho tất cả các API Order
public class OrderController {

    private final OrderService orderService;

    // Sử dụng Constructor Injection
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint Checkout
    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable Long userId) {
        // Gọi service để thực hiện logic đặt hàng phức tạp
        return orderService.checkout(userId);
    }

    // (Tùy chọn) Endpoint lấy Order theo ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        // OrderService cần có phương thức findById
        return orderService.findById(orderId);
    }
}