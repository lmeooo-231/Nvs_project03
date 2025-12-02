package k23cnt2.nvs_bansach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Sử dụng @Controller, không phải @RestController
public class HomeController {

    // Xử lý yêu cầu truy cập trang chủ: http://localhost:8080/
    @GetMapping("/")
    public String home() {
        // Trả về tên file HTML (trong thư mục templates)
        return "home"; // Sẽ tìm file templates/home.html
    }
}