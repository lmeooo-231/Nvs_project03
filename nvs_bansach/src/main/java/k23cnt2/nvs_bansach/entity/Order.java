package k23cnt2.nvs_bansach.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    // CHUYỂN ENUM RA NGOÀI:
    // Tốt nhất nên định nghĩa Enum này ở một file riêng (OrderStatus.java)
    // Hoặc giữ nguyên nếu bạn thích sử dụng Enum lồng nhau (nested enum)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Khóa Ngoại User (Bảng cha) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // user_id không được null
    private User user;

    @Column(nullable = false)
    private Double total;

    // --- Xử lý Enum Status ---
    // Đảm bảo kiểu dữ liệu lưu trong DB là chuỗi (String)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20) // Đảm bảo độ dài đủ và không null
    private OrderStatus status; // Sử dụng Enum đã định nghĩa

    @Column(name = "created_at", nullable = false, updatable = false) // Không thay đổi sau khi tạo
    private LocalDateTime createdAt;

    // (Tùy chọn) Thời gian cập nhật/hoàn thành
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Mối quan hệ với OrderItem (Bảng con) ---
    // CascadeType.ALL: Khi xóa Order, các OrderItem liên quan cũng bị xóa.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItem> orderItems;

    // Enum Status được đặt ở đây (hoặc file riêng)
    public enum OrderStatus {
        CREATED,    // Đơn hàng vừa được tạo (Checkout thành công)
        PROCESSING, // Đang xử lý
        SHIPPING,   // Đang giao hàng
        COMPLETED,  // Đã hoàn thành
        CANCELLED   // Đã hủy
    }

    // --- Logic tự động thiết lập thời gian khi tạo ---
    // @PrePersist được gọi trước khi lưu Entity lần đầu tiên
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        // Đặt trạng thái mặc định (Default status) nếu chưa được đặt
        if (this.status == null) {
            this.status = OrderStatus.CREATED;
        }
    }

    // @PreUpdate được gọi trước khi cập nhật Entity
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}