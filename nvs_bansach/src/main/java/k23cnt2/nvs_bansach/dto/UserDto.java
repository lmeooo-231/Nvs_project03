package k23cnt2.nvs_bansach.dto;

import k23cnt2.nvs_bansach.entity.User;
import lombok.Data;

@Data // Lombok tự sinh Getter/Setter

public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    // Constructor để chuyển từ Entity sang DTO
    public UserDto(User user) {
        this.id = user.getId();
        this.fullName = user.getFullname(); // Lưu ý: dùng getFullname()
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole().name();
    }
}