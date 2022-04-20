package uz.isystem.market.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.create(orderDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id,
                                         @Valid @RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.update(id, orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderService.getAll());
    }
}
