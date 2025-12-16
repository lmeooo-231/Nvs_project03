package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.dto.UserDto;
import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Endpoint: GET /api/users/profile
    // Đã sửa lỗi biên dịch bằng cách sử dụng Optional<User> từ UserService.
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getMyProfile() { // 🚨 THAY ĐỔI KIỂU TRẢ VỀ
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }

        String userEmail;
        Object principal = authentication.getPrincipal();

        // Giả định principal là đối tượng User Entity (được JwtAuthenticationFilter đặt vào)
        if (principal instanceof User) {
            userEmail = ((User) principal).getEmail();
        } else if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            return ResponseEntity.status(400).build(); // Lỗi không xác định principal
        }


        Optional<User> userOptional = userService.findUserByEmail(userEmail);

        return userOptional
                .map(UserDto::new) // 🚨 CHUYỂN ENTITY SANG DTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // 2. Endpoint: GET /api/users
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(UserDto::new) // 🚨 CHUYỂN ĐỔI LIST SANG DTO
                .toList();
    }

    // 3. Endpoint: GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Endpoint: POST /api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 5. Endpoint: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}