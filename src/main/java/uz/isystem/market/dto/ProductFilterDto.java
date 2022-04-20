package uz.isystem.market.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductFilterDto extends FilterDto{
    private String name;
    private Double price;
    private Integer rate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
