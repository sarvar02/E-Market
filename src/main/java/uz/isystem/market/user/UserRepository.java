package uz.isystem.market.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByIdAndDeletedAtIsNull(Integer id);

    Optional<User> findByContactAndDeletedAtIsNull(String contact);

    Optional<User> findByContactAndEmail(String contact, String email);

    @Query("from User where password = :password and contact = :contact")
    @Transactional
    Optional<User> authorize(@Param("password") String password, @Param("contact") String contact);

    List<User> findAllByDeletedAtIsNull();
}
