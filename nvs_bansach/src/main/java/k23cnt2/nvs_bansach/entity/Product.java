package k23cnt2.nvs_bansach.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products") // Ánh xạ tới bảng 'products'
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Lob // Dùng cho các trường văn bản lớn (TEXT)
    private String description;

    private Double price;

    private Integer stock;

    private String image;

    // Mối quan hệ ManyToOne: Một sản phẩm thuộc về một Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Ánh xạ tới khóa ngoại 'category_id'
    private Category category;

    // Mối quan hệ với CartItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartItem> cartItems;

    // Mối quan hệ với OrderItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems;

    // Mối quan hệ với Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Review> reviews;
}