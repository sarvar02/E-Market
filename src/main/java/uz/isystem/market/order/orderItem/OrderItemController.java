package uz.isystem.market.order.orderItem;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/order-item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderItemById(@PathVariable Integer id){
        return ResponseEntity.ok(orderItemService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto){
        return ResponseEntity.ok(orderItemService.create(orderItemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Integer id,
                                             @Valid @RequestBody OrderItemDto orderItemDto){
        return ResponseEntity.ok(orderItemService.update(id, orderItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Integer id){
        return ResponseEntity.ok(orderItemService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrderItems(){
        return ResponseEntity.ok(orderItemService.getAll());
    }
}
