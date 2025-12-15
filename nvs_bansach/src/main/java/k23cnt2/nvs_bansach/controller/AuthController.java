package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.dto.AuthResponse; // Import AuthResponse
import k23cnt2.nvs_bansach.dto.LoginRequest; // Import LoginRequest
import k23cnt2.nvs_bansach.dto.RegisterRequest;
import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 1. API Đăng ký: POST /api/auth/register (Giữ nguyên)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User registeredUser = authService.registerUser(request);
            return ResponseEntity.ok("Đăng ký thành công! User ID: " + registeredUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. 🚨 API Đăng nhập: POST /api/auth/login (Mới)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // AuthService sẽ xử lý việc xác thực, tạo JWT và trả về AuthResponse
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}