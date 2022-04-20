package uz.isystem.market.product.productType;

import org.springframework.stereotype.Service;
import uz.isystem.market.exception.ServerBadRequestException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ProductTypeDto get(Integer id){
        return convertEntityToDto(getEntity(id), new ProductTypeDto());
    }

    public ProductType create(ProductTypeDto productTypeDto){
        ProductType productType = convertDtoToEntity(productTypeDto, new ProductType());
        productType.setStatus("Faol");
        productType.setCreatedAt(LocalDateTime.now());
        return productTypeRepository.save(productType);
    }

    public List<ProductTypeDto> getAll(){
        List<ProductType> productTypeDtoList = productTypeRepository.findAllByDeletedAtIsNull();
        if(productTypeDtoList.isEmpty()) throw new ServerBadRequestException("Product type not found !");
        return productTypeDtoList.stream()
                .map(productType -> convertEntityToDto(productType, new ProductTypeDto()))
                .collect(Collectors.toList());
    }

    public ProductType update(Integer id, ProductTypeDto productTypeDto){
        ProductType productType = getEntity(id);
        productType = convertDtoToEntity(productTypeDto, productType);
        productType.setUpdatedAt(LocalDateTime.now());
        return productTypeRepository.save(productType);
    }

    public String delete(Integer id){
        ProductType productType = getEntity(id);
        productType.setDeletedAt(LocalDateTime.now());
        productTypeRepository.save(productType);
        return "Product type successfully deleted !";
    }


    // Secondary functions

    public ProductType getEntity(Integer id){
        return productTypeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ServerBadRequestException("Product type not found !"));
    }

    public ProductTypeDto convertEntityToDto(ProductType productType, ProductTypeDto productTypeDto){
        productTypeDto.setId(productType.getId());
        productTypeDto.setName(productType.getName());
        productTypeDto.setStatus(productType.getStatus());
        productTypeDto.setCreatedAt(productType.getCreatedAt());
        productTypeDto.setUpdatedAt(productType.getUpdatedAt());
        productTypeDto.setDeletedAt(productType.getDeletedAt());

        return productTypeDto;
    }

    public ProductType convertDtoToEntity(ProductTypeDto productTypeDto, ProductType productType){
        productType.setName(productTypeDto.getName());

        return productType;
    }
}
