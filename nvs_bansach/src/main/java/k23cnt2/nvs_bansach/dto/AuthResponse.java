package k23cnt2.nvs_bansach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    // JWT Token được tạo ra sau khi đăng nhập thành công
    private String token;

    // Loại token, thường là "Bearer "
    private String type = "Bearer";

    // ID của người dùng (giúp Frontend dễ dàng truy cập giỏ hàng/lịch sử)
    private Long id;

    // Email của người dùng
    private String email;

    // Quyền hạn của người dùng (USER, ADMIN)
    private String role;
}