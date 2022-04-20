package uz.isystem.market.product.productType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/product-type")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductTypeById(@PathVariable Integer id){
        return ResponseEntity.ok(productTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createProductType(@Valid @RequestBody ProductTypeDto productTypeDto){
        return ResponseEntity.ok(productTypeService.create(productTypeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody ProductTypeDto productTypeDto){
        return ResponseEntity.ok(productTypeService.update(id, productTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable Integer id){
        return ResponseEntity.ok(productTypeService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productTypeService.getAll());
    }
}
