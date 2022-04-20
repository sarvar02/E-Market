package uz.isystem.market.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDto {
    private String password;
    private String checkPassword;
    private String contact;
    private String email;
}