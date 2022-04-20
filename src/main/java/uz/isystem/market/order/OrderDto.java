package uz.isystem.market.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.user.User;
import uz.isystem.market.user.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Integer id;
    private UserDto userDto;
    @NotNull(message = "User id cannot be null")
    private Integer userId;
    private LocalDateTime deliveryDate;
    private String requirement;
    @NotBlank(message = "You have to write your contact")
    private String contact;
    @NotBlank(message = "You have to write your address")
    private String address;
    private LocalDateTime deliveredDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
