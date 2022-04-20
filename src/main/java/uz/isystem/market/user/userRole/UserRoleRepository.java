package uz.isystem.market.user.userRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByIdAndDeletedAtIsNull(Integer id);

    List<UserRole> findByNameAndDeletedAtIsNull(String name);

    List<UserRole> findAllByDeletedAtIsNull();
}
