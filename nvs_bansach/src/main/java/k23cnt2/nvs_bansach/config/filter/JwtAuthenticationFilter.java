package k23cnt2.nvs_bansach.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import k23cnt2.nvs_bansach.service.JwtService;
import k23cnt2.nvs_bansach.service.UserService; // 🚨 Đã thay thế UserDetailsService
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserService userService; // 🚨 SỬA LỖI: Tiêm UserService

    // SỬA CONSTRUCTOR
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

            // SỬA LỖI: Sử dụng UserService để tìm UserDetails
            UserDetails userDetails = this.userService.findUserByEmail(userEmail);

            // 🚨 LOG MỚI: Kiểm tra tính hợp lệ của Token
            boolean isValid = jwtService.isTokenValid(jwt, userDetails);
            log.info("2. Token hợp lệ (isTokenValid): {}", isValid); // Log 2

            if (isValid) {
                log.info("3. Token HỢP LỆ. Thiết lập Authentication."); // Log 3

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken();
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                log.warn("Token KHÔNG HỢP LỆ. Bị từ chối."); // Log 4
            }
        }

        filterChain.doFilter(request, response);
    }
}