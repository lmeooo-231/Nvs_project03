package k23cnt2.nvs_bansach.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Sử dụng Lombok để tự động tạo getters/setters và constructors
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}