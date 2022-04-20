package uz.isystem.market.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.create(productDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
                                           @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        return ResponseEntity.ok(productService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }
}
