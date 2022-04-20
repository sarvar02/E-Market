package uz.isystem.market.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.user.userRole.UserRoleDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer id;
    @NotBlank(message = "You have to enter your name, please !")
    private String name;
    private String surname;
    @NotBlank(message = "You have to enter your email, please !")
    private String email;
    @NotBlank(message = "Please create a password for security, please !")
    private String password;
    private String contact;
    private Integer imageId;
    private String status;
    private UserRoleDto userRoleDto;
    @NotNull(message = "You have to choose one user role, please !")
    private Integer userRoleId;
    private String token;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
