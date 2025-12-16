package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Sử dụng Dependency Injection (DI) thông qua constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy tất cả người dùng
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Tìm người dùng theo ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Lưu/Cập nhật người dùng
    public User saveUser(User user) {
        // (Nên thêm logic nghiệp vụ, ví dụ: mã hóa mật khẩu tại đây)
        return userRepository.save(user);
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ======================================================
    // SỬA: Thay đổi kiểu trả về từ User sang Optional<User>
    // ======================================================
    public Optional<User> findUserByEmail(String email) {
        // Giả định UserRepository có phương thức findByEmail trả về Optional
        // Nếu UserRepository đã trả về Optional, ta chỉ cần return nó.
        return userRepository.findByEmail(email);

        /* * LƯU Ý: userRepository.findByEmail(String email) PHẢI trả về Optional<User>.
         * Nếu phương thức này trong Repository của bạn trả về User,
         * bạn sẽ cần phải sửa lại Repository hoặc bọc kết quả:
         * * return Optional.ofNullable(userRepository.findByEmail(email));
         */
    }
}