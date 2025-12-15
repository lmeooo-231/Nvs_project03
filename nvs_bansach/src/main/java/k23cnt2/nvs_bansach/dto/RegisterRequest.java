package k23cnt2.nvs_bansach.dto;

import lombok.Data;

@Data // Lombok tự sinh Getter/Setter
public class RegisterRequest {
    private String fullname;
    private String email;
    private String password;
    private String phone;
}