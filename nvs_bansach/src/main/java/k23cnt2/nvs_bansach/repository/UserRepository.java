package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user bằng email (Dùng cho Đăng nhập)
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã tồn tại chưa (Dùng cho Đăng ký)
    Boolean existsByEmail(String email);
}