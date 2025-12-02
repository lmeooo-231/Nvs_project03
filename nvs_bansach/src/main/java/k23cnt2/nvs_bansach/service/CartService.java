package k23cnt2.nvs_bansach.service;

import k23cnt2.nvs_bansach.dto.CartItemRequest; // Yêu cầu bạn đã tạo DTO này
import k23cnt2.nvs_bansach.entity.CartItem;
import k23cnt2.nvs_bansach.entity.Product;
import k23cnt2.nvs_bansach.entity.User;
import k23cnt2.nvs_bansach.repository.CartRepository;
import k23cnt2.nvs_bansach.repository.ProductRepository;
import k23cnt2.nvs_bansach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // =========================================================================
    // 1. THÊM SẢN PHẨM VÀO GIỎ HÀNG (ADD TO CART)
    // =========================================================================

    @Transactional
    public CartItem addProductToCart(CartItemRequest request) {
        // 1. Tìm User và Product (Đảm bảo chúng tồn tại)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        int quantityToAdd = request.getQuantity();

        // 2. Kiểm tra xem CartItem đã tồn tại chưa
        // 🚨 Yêu cầu CartRepository có phương thức findByUserAndProduct
        CartItem existingItem = cartRepository.findByUserAndProduct(user, product);

        if (existingItem != null) {
            // Trường hợp 1: Sản phẩm đã có trong giỏ hàng (Cập nhật số lượng)
            int newQuantity = existingItem.getQuantity() + quantityToAdd;

            // Kiểm tra tồn kho (Nếu tổng số lượng vượt quá)
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Số lượng trong giỏ hàng vượt quá tồn kho hiện tại.");
            }

            existingItem.setQuantity(newQuantity);
            return cartRepository.save(existingItem);

        } else {
            // Trường hợp 2: Sản phẩm chưa có trong giỏ hàng (Tạo mới)

            // Kiểm tra tồn kho cho số lượng thêm mới
            if (product.getStock() < quantityToAdd) {
                throw new RuntimeException("Số lượng thêm mới vượt quá tồn kho hiện tại.");
            }

            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            // 🚨 Yêu cầu CartItem có setQuantity
            newItem.setQuantity(quantityToAdd);

            return cartRepository.save(newItem);
        }
    }

    // =========================================================================
    // 2. XEM GIỎ HÀNG (GET CART)
    // =========================================================================

    public List<CartItem> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 🚨 Yêu cầu CartRepository có phương thức findByUser
        return cartRepository.findByUser(user);
    }

    // =========================================================================
    // 3. (Tùy chọn) XÓA CART ITEM
    // =========================================================================

    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
}