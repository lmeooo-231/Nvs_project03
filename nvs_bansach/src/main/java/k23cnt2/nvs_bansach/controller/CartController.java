package k23cnt2.nvs_bansach.controller;

import k23cnt2.nvs_bansach.entity.CartItem;
import k23cnt2.nvs_bansach.service.CartService;
import org.springframework.web.bind.annotation.*;
import k23cnt2.nvs_bansach.dto.CartItemRequest;
import java.util.List;

@RestController // REST API cho Giỏ hàng
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Endpoint GET /api/cart/1 (Lấy giỏ hàng của user ID 1)
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    // Endpoint POST /api/cart/add
    // Thân request: { "userId": 1, "productId": 1, "quantity": 1 }
    @PostMapping("/add")
    public CartItem addItemToCart(@RequestBody CartItemRequest request) {
        // 🚨 SỬA LỖI: Chỉ truyền đối tượng DTO 'request' vào service
        return cartService.addProductToCart(request);
    }
}
