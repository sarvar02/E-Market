package uz.isystem.market.order.orderItem;

import org.springframework.stereotype.Service;
import uz.isystem.market.exception.ServerBadRequestException;
import uz.isystem.market.order.OrderService;
import uz.isystem.market.product.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderService orderService, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public OrderItemDto get(Integer id){
        return convertEntityToDto(getEntity(id), new OrderItemDto());
    }

    public OrderItem create(OrderItemDto orderItemDto){
        OrderItem orderItem = convertDtoToEntity(orderItemDto, new OrderItem());
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setStatus("Faol");
        return orderItemRepository.save(orderItem);
    }

    public String delete(Integer id){
        OrderItem orderItem = getEntity(id);
        orderItem.setDeletedAt(LocalDateTime.now());
        orderItemRepository.save(orderItem);
        return "Order item successfully deleted !";
    }

    public List<OrderItemDto> getAll(){
        List<OrderItem> orderItemList = orderItemRepository.findAllByDeletedAtIsNull();
        if(orderItemList.isEmpty()){
            throw new ServerBadRequestException("Order item not found !");
        }
        return orderItemList.stream()
                .map(orderItem -> convertEntityToDto(orderItem, new OrderItemDto()))
                .collect(Collectors.toList());
    }

    public OrderItem update(Integer id, OrderItemDto orderItemDto){
        OrderItem orderItem = getEntity(id);
        orderItem = convertDtoToEntity(orderItemDto, orderItem);
        orderItem.setUpdatedAt(LocalDateTime.now());
        return orderItemRepository.save(orderItem);
    }





    // Secondary functions

    public OrderItem getEntity(Integer id){
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ServerBadRequestException("Order item not found !"));
    }

    public OrderItemDto convertEntityToDto(OrderItem orderItem, OrderItemDto orderItemDto){
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setOrderId(orderItem.getOrderId());
        orderItemDto.setOrderDto(orderService.get(orderItem.getOrderId()));
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setProductDto(productService.get(orderItem.getProductId()));
        orderItemDto.setStatus(orderItem.getStatus());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        orderItemDto.setUpdatedAt(orderItem.getUpdatedAt());
        orderItemDto.setDeletedAt(orderItem.getDeletedAt());

        return orderItemDto;
    }

    public OrderItem convertDtoToEntity(OrderItemDto orderItemDto, OrderItem orderItem){
        orderItem.setOrderId(orderItemDto.getOrderId());
        orderItem.setProductId(orderItemDto.getProductId());

        return orderItem;
    }
}
