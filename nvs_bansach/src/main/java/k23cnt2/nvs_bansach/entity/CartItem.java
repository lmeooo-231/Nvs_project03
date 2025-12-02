package k23cnt2.nvs_bansach.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter // Bắt buộc cho các phương thức getXyz()
@Setter // Bắt buộc cho các phương thức setXyz()
@NoArgsConstructor // Cần thiết cho JPA
@AllArgsConstructor
@Table(name = "cart_items") // Ánh xạ tới bảng cart_items
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mối quan hệ ManyToOne với User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Mối quan hệ ManyToOne với Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}