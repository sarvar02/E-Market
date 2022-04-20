package uz.isystem.market.product.productType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    Optional<ProductType> findByIdAndDeletedAtIsNull(Integer id);

    List<ProductType> findAllByDeletedAtIsNull();
}
