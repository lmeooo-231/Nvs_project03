package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.dto.AuthResponse;
import k23cnt2.nvs_bansach.dto.LoginRequest;
import k23cnt2.nvs_bansach.dto.RegisterRequest;
import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.entity.UserRole;
import k23cnt2.nvs_bansach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 🚨 BỔ SUNG JWT SERVICE VÀ AUTHENTICATION MANAGER (Cho chức năng Đăng nhập)
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // =========================================================================
    // 1. CHỨC NĂNG ĐĂNG KÝ (REGISTER)
    // =========================================================================
    public User registerUser(RegisterRequest request) {
        // Tạo đối tượng User
        User newUser = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.USER) // 🚨 THÊM DÒNG NÀY ĐỂ GÁN ROLE MẶC ĐỊNH
                .build();

        return userRepository.save(newUser);
    }

    // =========================================================================
    // 2. CHỨC NĂNG ĐĂNG NHẬP (LOGIN)
    // =========================================================================
    public AuthResponse login(LoginRequest request) {
        // 1. Xác thực bằng Username (Email) và Password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Lấy UserDetails từ kết quả xác thực
        // Chúng ta cần một class tùy chỉnh (Custom UserDetails) để lấy User ID và Role
        // Tạm thời, ta tìm lại User từ DB sau khi xác thực thành công.
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Tạo JWT Token
        String jwtToken = jwtService.generateToken(user);

        // 4. Trả về thông tin đăng nhập và Token
        return new AuthResponse(
                jwtToken,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}