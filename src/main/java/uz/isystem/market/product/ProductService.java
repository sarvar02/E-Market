package uz.isystem.market.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.isystem.market.dto.ProductFilterDto;
import uz.isystem.market.exception.ServerBadRequestException;
import uz.isystem.market.product.productType.ProductTypeService;
import uz.isystem.market.user.User;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;

    public ProductDto get(Integer id){
        return convertEntityToDto(getEntity(id), new ProductDto());
    }

    public Product create(ProductDto productDto){
        Product product = convertDtoToEntity(productDto, new Product());
        product.setStatus("Faol");
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    public List<ProductDto> getAll(){
        List<Product> products = productRepository.findAllByDeletedAtIsNull();
        if(products.isEmpty()){
            throw new ServerBadRequestException("Product not found !");
        }
        return products.stream()
                .map(product -> convertEntityToDto(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    public Product update(Integer id, ProductDto productDto){
        Product product = getEntity(id);
        product = convertDtoToEntity(productDto, product);
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }


    public String delete(Integer id){
        Product product = getEntity(id);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
        return "Product successfully deleted !";
    }


    //    <-------------   FILTERING   ------------->     //

    public List<Product> filter(ProductFilterDto filterDto){
        String sortBy = filterDto.getSortBy();
        if(sortBy == null){
            sortBy = "createdAt";
        }

        Pageable pageable =  PageRequest.of(filterDto.getPage(), filterDto.getSize(), filterDto.getDirection(), sortBy);
        List<Predicate> predicateList = new LinkedList<>();
        Specification<Product> specification = ((root, query, criteriaBuilder) -> {
            if(filterDto.getName() != null){
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + filterDto.getName() + "%"));
            }
            if(filterDto.getPrice() != null){
                predicateList.add(criteriaBuilder.like(root.get("surname"), "%" + filterDto.getPrice() + "%"));
            }
            if(filterDto.getRate() != null){
                predicateList.add(criteriaBuilder.like(root.get("subject"), "%" + filterDto.getRate() + "%"));
            }
            if(filterDto.getStartDate() != null && filterDto.getEndDate() != null){
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        filterDto.getStartDate(), filterDto.getEndDate()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });

        Page<Product> certificatePage = productRepository.findAll(specification, pageable);
        return certificatePage.get().collect(Collectors.toList());
    }

    //     <-------------------------->     //




    // Secondary functions

    public Product getEntity(Integer id){
        return productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ServerBadRequestException("Product not found !"));
    }

    public ProductDto convertEntityToDto(Product product, ProductDto productDto){
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setIsVisible(product.getIsVisible());
        productDto.setRate(product.getRate());
        productDto.setProductTypeId(product.getProductTypeId());
        productDto.setProductTypeDto(productTypeService.get(product.getProductTypeId()));
        productDto.setStatus(product.getStatus());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setDeletedAt(product.getDeletedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());

        return productDto;
    }

    public Product convertDtoToEntity(ProductDto productDto, Product product){
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setIsVisible(productDto.getIsVisible());
        product.setProductTypeId(productDto.getProductTypeId());

        return product;
    }
}
