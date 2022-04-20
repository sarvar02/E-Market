package uz.isystem.market.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Entity
@Table(name = ("orders"))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ("user_id"), insertable = false, updatable = false)
    private User user;

    @Column(name = ("user_id"))
    private Integer userId;

    private LocalDateTime deliveryDate;

    private String requirement;

    private String contact;

    private String address;

    private LocalDateTime deliveredDate;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;
}
