package k23cnt2.nvs_bansach.config;

import k23cnt2.nvs_bansach.config.filter.JwtAuthenticationFilter;
import k23cnt2.nvs_bansach.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserRepository userRepository;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserRepository userRepository) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
    }

    // 1. Bean mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Bean Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 3. Bean User Detail Service
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    }

    // 4. Cấu hình luồng bảo mật (Security Filter Chain)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // 1. QUY TẮC CÓ PHÂN QUYỀN (Role-based) - Ưu tiên hàng đầu
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 2. CÁC ĐƯỜNG DẪN CÔNG KHAI (permitAll) - Trang UI, Auth API, và Tài nguyên tĩnh
                        .requestMatchers(
                                "/",
                                "/api/auth/**",
                                "/api/products/**",
                                "/products",
                                "/cart",
                                "/product-detail",
                                "/login",
                                "/register",
                                "/checkout",
                                "/order-success",
                                "/profile",
                                "/css/**", "/js/**",
                                "/images/**"
                        ).permitAll()

                        // 3. CÁC ĐƯỜNG DẪN YÊU CẦU XÁC THỰC CHUNG (authenticated) - Trang Profile
                        // Lưu ý: api/users/profile đã được gộp vào đây
                        .requestMatchers("/profile", "/api/users/profile").authenticated()

                        // 4. BẤT KỲ YÊU CẦU NÀO CÒN LẠI (anyRequest) - LUÔN LÀ CUỐI CÙNG
                        .anyRequest().authenticated()
                )

                // Cấu hình Session là STATELESS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // ÁP DỤNG JWT FILTER
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}