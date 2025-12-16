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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == null) {

            return Collections.emptyList();
        }

        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }


    @Override
    public String getUsername() {

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