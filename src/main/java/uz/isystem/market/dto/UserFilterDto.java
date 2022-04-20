package uz.isystem.market.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserFilterDto extends FilterDto{
    private String name;
    private String surname;
    private String email;
    private String contact;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
