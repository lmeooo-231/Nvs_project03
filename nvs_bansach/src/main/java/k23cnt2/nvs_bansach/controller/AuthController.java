package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.entity.UserRole;
import k23cnt2.nvs_bansach.service.JwtService; // Giả định bạn có JwtService
import k23cnt2.nvs_bansach.entity.User; // Giả định model User
import k23cnt2.nvs_bansach.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// --- DTOs (Data Transfer Objects) MẪU ---
// Trong thực tế, bạn nên đặt chúng trong một package riêng (ví dụ: dto/)
class RegisterRequest {
    public String fullName;
    public String email;
    public String password;
}

class AuthenticationRequest {
    public String email;
    public String password;
}

class AuthenticationResponse {
    public String token;
    public String message = "Authentication successful";

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
// ----------------------------------------

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService; // Giả định đã tiêm JwtService

    // Constructor Injection
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * API ĐĂNG KÝ: POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 1. Kiểm tra Email đã tồn tại
        if (userRepository.findByEmail(request.email).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email đã tồn tại. Vui lòng sử dụng email khác.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // 2. Tạo User mới
        var user = new User();
        user.setFullname(request.fullName);
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        // Mặc định quyền USER
        user.setRole(UserRole.USER);

        userRepository.save(user);

        // 3. Trả về token và thông báo thành công (Tùy chọn: trả về token ngay sau khi đăng ký)
        UserDetails userDetails = user;
        String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    /**
     * API ĐĂNG NHẬP: POST /api/auth/authenticate
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // 1. Xác thực bằng UsernamePasswordAuthenticationToken
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email,
                            request.password
                    )
            );

            // 2. Nếu xác thực thành công, tạo JWT Token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            // 3. Trả về Token
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));

        } catch (Exception e) {
            // 4. Nếu xác thực thất bại
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email hoặc mật khẩu không đúng.");
            // Trả về 401 Unauthorized (hoặc 403 Forbidden tùy cấu hình)
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }
}