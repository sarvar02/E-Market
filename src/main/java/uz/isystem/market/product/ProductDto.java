package uz.isystem.market.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.product.productType.ProductType;
import uz.isystem.market.product.productType.ProductTypeDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Integer id;
    @NotBlank(message = "Product name cannot be blank !")
    private String name;
    private String description;
    @NotNull(message = "Please, enter product's price !")
    private Double price;
    private Integer rate;
    private Boolean isVisible;
    private ProductTypeDto productTypeDto;
    @NotNull(message = "Please, choose a product type !")
    private Integer productTypeId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
