package k23cnt2.nvs_bansach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/products")
    public String productListPage() {
        return "product-list";
    }

    /**
     * PHƯƠNG THỨC MỚI: Xử lý yêu cầu tới đường dẫn "/cart"
     * Trả về tên file HTML: cart.html
     */
    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    /**
     * PHƯƠNG THỨC MỚI: Xử lý yêu cầu tới đường dẫn "/product-detail"
     * Trả về tên file HTML: product-detail.html
     */
    @GetMapping("/product-detail")
    public String productDetailPage() {
        return "product-detail";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout";
    }

    /**
     * PHƯƠNG THỨC MỚI: Xử lý yêu cầu tới đường dẫn "/order-success"
     * Trả về tên file HTML: order-success.html
     */
    @GetMapping("/order-success")
    public String orderSuccessPage() {
        return "order-success";
    }
    @GetMapping("/profile")
    public String profilePage() {
        // Lưu ý: Sau này, phương thức này cần được bảo mật bằng Spring Security
        // (chỉ cho phép user đã đăng nhập truy cập)
        return "profile";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * PHƯƠNG THỨC MỚI: Xử lý yêu cầu tới đường dẫn "/register"
     * Trả về tên file HTML: register.html
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

}