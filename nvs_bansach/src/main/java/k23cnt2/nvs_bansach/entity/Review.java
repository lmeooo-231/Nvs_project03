package k23cnt2.nvs_bansach.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews") // Ánh xạ tới bảng 'reviews'
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Đảm bảo kiểu dữ liệu khớp với DB (rating INT CHECK (rating BETWEEN 1 AND 5))
    private Integer rating;

    @Lob // Dùng cho TEXT
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}