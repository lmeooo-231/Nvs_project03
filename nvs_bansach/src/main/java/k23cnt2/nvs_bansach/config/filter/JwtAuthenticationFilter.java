package k23cnt2.nvs_bansach.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import k23cnt2.nvs_bansach.entity.User; // Cần import User entity
import k23cnt2.nvs_bansach.service.JwtService;
import k23cnt2.nvs_bansach.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional; // Cần import Optional

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // =================================================================
            // SỬA CHỮA QUAN TRỌNG: Xử lý Optional và chuyển đổi sang UserDetails
            // =================================================================
            Optional<User> userOptional = this.userService.findUserByEmail(userEmail);

            if (userOptional.isPresent()) {
                // Giả định User entity của bạn implement UserDetails,
                // hoặc bạn có logic chuyển đổi từ User sang UserDetails.
                // Nếu User entity không implement UserDetails, dòng này sẽ gây lỗi.
                // Ở đây, ta giả định User đã implement UserDetails.
                UserDetails userDetails = userOptional.get();

                boolean isValid = jwtService.isTokenValid(jwt, userDetails);
                log.info("2. Token hợp lệ (isTokenValid): {}", isValid);

                if (isValid) {
                    log.info("3. Token HỢP LỆ. Thiết lập Authentication.");

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (
                            userDetails,
                            null, // Không cần password ở đây
                            userDetails.getAuthorities() // CUNG CẤP VAI TRÒ (AUTHORITIES) ĐỂ KHẮC PHỤC 403
                    );

                    // Thêm chi tiết web authentication (thực hành tốt)
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.warn("Token KHÔNG HỢP LỆ. Bị từ chối.");
                }
            } else {
                log.warn("Không tìm thấy User cho email: {}", userEmail);
            }
        }

        filterChain.doFilter(request, response);
    }
}