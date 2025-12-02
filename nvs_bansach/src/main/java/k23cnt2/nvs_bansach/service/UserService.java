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

    // Tìm theo Email (dùng phương thức tùy chỉnh từ Repository)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}