package k23cnt2.nvs_bansach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // Thiết lập tiền tố cho tất cả các request trong Controller này
public class AdminController {

    /**
     * Xử lý yêu cầu tới /admin/ hoặc /admin/dashboard
     * Trả về tên file HTML: admin/dashboard.html
     */
    @GetMapping({"", "/dashboard"})
    public String adminDashboard() {
        return "admin/dashboard";
    }

    /**
     * Xử lý yêu cầu tới /admin/products (Quản lý Sản phẩm)
     * Trả về tên file HTML: admin/products.html
     */
    @GetMapping("/products")
    public String adminProducts() {
        return "admin/products";
    }

    /**
     * Xử lý yêu cầu tới /admin/orders (Quản lý Đơn hàng)
     * Trả về tên file HTML: admin/orders.html
     */
    @GetMapping("/orders")
    public String adminOrders() {
        return "admin/orders";
    }
}