package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Đánh dấu đây là một REST Controller (trả về JSON/XML)
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint: GET /api/users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // Endpoint: GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint: POST /api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        // LƯU Ý: Tuyệt đối không lưu mật khẩu trực tiếp, phải mã hóa trước!
        return userService.saveUser(user);
    }

    // Endpoint: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}