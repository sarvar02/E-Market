package uz.isystem.market.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.isystem.market.user.userRole.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = ("users"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String contact;

    private Integer imageId;

    private String status;

    @ManyToOne
    @JoinColumn(name = ("user_role_id"), insertable = false, updatable = false)
    private UserRole userRole;

    @Column(name = ("user_role_id"))
    private Integer userRoleId;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;
}
