package uz.isystem.market.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.isystem.market.exception.ServerBadRequestException;
import uz.isystem.market.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderDto get(Integer id){
        return convertEntityToDto(getEntity(id), new OrderDto());
    }

    public Order create(OrderDto orderDto){
        Order order = convertDtoToEntity(orderDto, new Order());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("Faol");
        return orderRepository.save(order);
    }

    public List<OrderDto> getAll(){
        List<Order> orderList = orderRepository.findAllByDeletedAtIsNull();
        if(orderList.isEmpty()) throw new ServerBadRequestException("Order not found !");
        return orderList.stream()
                .map(order -> convertEntityToDto(order, new OrderDto()))
                .collect(Collectors.toList());
    }

    public Order update(Integer id, OrderDto orderDto){
        Order order = getEntity(id);
        order = convertDtoToEntity(orderDto, order);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public String delete(Integer id){
        Order order = getEntity(id);
        order.setDeletedAt(LocalDateTime.now());
        orderRepository.save(order);
        return "Orders successfully deleted !";
    }



    // Secondary functions

    public Order getEntity(Integer id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new ServerBadRequestException("Order not found !"));
    }

    public OrderDto convertEntityToDto(Order order, OrderDto orderDto){
        orderDto.setId(order.getId());
        orderDto.setAddress(order.getAddress());
        orderDto.setContact(order.getContact());
        orderDto.setDeliveryDate(order.getDeliveryDate());
        orderDto.setDeliveredDate(order.getDeliveredDate());
        orderDto.setRequirement(order.getRequirement());
        orderDto.setUserId(order.getUserId());
        orderDto.setUserDto(userService.get(order.getUserId()));
        orderDto.setStatus(order.getStatus());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setDeletedAt(order.getDeletedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());

        return orderDto;
    }

    public Order convertDtoToEntity(OrderDto orderDto, Order order){
        order.setAddress(orderDto.getAddress());
        order.setContact(orderDto.getContact());
        order.setDeliveryDate(orderDto.getDeliveryDate());
        order.setRequirement(orderDto.getRequirement());
        order.setUserId(orderDto.getUserId());

        return order;
    }
}
