package k23cnt2.nvs_bansach.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // Ánh xạ tới bảng 'users' trong database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(unique = true) // Đảm bảo email là duy nhất
    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Mối quan hệ với CartItem
    // mappedBy: tên thuộc tính 'user' trong lớp CartItem
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // Quan trọng: Loại trừ để tránh lỗi StackOverflow
    private List<CartItem> cartItems;

    // Mối quan hệ với Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders;

    // Mối quan hệ với Review
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Review> reviews;

    // (Bạn có thể thêm created_at nếu cần)
}