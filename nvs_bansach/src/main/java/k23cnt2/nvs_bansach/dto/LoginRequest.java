package k23cnt2.nvs_bansach.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}