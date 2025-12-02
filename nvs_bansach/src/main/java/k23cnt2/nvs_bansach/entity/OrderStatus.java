package k23cnt2.nvs_bansach.entity;

public enum OrderStatus {
    CREATED,    // Đơn hàng vừa được tạo (Checkout thành công)
    PENDING,    // Đang chờ xác nhận/thanh toán
    SHIPPED,    // Đã giao cho đơn vị vận chuyển
    DELIVERED,  // Đã giao hàng thành công
    CANCELLED   // Đã hủy
}