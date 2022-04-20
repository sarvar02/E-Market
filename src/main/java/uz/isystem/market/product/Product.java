package uz.isystem.market.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.product.productType.ProductType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = ("product"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer rate;

    private Boolean isVisible;

    @ManyToOne
    @JoinColumn(name = ("product_type_id"), insertable = false, updatable = false)
    private ProductType productType;

    @Column(name = ("product_type_id"))
    private Integer productTypeId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;
}
