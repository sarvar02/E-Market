package uz.isystem.market.order.orderItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.order.Order;
import uz.isystem.market.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Entity
@Table(name = ("order_items"))
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ("order_id"), insertable = false, updatable = false)
    private Order order;

    @Column(name = ("order_id"))
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = ("product_id"), insertable = false, updatable = false)
    private Product product;

    @Column(name = ("product_id"))
    private Integer productId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;
}
