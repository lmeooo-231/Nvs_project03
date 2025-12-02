package k23cnt2.nvs_bansach.repository;

import k23cnt2.nvs_bansach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Thêm các phương thức truy vấn tùy chỉnh tại đây
    // Ví dụ: tìm kiếm người dùng theo email
    User findByEmail(String email);
}