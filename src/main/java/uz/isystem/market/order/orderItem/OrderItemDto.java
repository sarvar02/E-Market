package uz.isystem.market.order.orderItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.order.Order;
import uz.isystem.market.order.OrderDto;
import uz.isystem.market.product.Product;
import uz.isystem.market.product.ProductDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDto {
    private Integer id;
    private OrderDto orderDto;
    @NotNull(message = "Order id cannot be null")
    private Integer orderId;
    private ProductDto productDto;
    @NotNull(message = "Product id cannot be null")
    private Integer productId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
