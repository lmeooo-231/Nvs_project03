package k23cnt2.nvs_bansach.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // 🚨 IMPORT NÀY RẤT QUAN TRỌNG

@Entity
@Getter
@Setter
@Builder // Thêm @Builder để dễ tạo đối tượng User trong tương lai
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
// 🚨 BƯỚC SỬA LỖI: Implement UserDetails
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(unique = true)
    private String email;

    private String password; // 🚨 Cần dùng trường này

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role; // 🚨 Cần dùng trường này

    // ... (Các mối quan hệ OneToMany khác) ...

    // =========================================================================
    // 🚨 PHƯƠNG THỨC BẮT BUỘC CỦA USERDETAILS (Dành cho Spring Security)
    // =========================================================================

    // 1. Cung cấp quyền hạn (Authorities)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 🚨 SỬA LỖI: Thêm kiểm tra Null cho 'role'
        if (this.role == null) {
            // Nếu role bị null, trả về danh sách rỗng để tránh NPE
            return Collections.emptyList();
        }
        // Trả về một Collection chứa quyền của User (ví dụ: "ROLE_USER" hoặc "ROLE_ADMIN")
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // 2. Tên người dùng (Tên đăng nhập)
    @Override
    public String getUsername() {
        // Trong hệ thống của bạn, email được dùng làm username
        return email;
    }

    // 3. Mật khẩu
    @Override
    public String getPassword() {
        // Trả về trường password của Entity User
        return password;
    }

    // 4. Các phương thức kiểm tra trạng thái tài khoản (Thường để mặc định là true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}