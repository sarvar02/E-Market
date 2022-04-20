package uz.isystem.market.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.isystem.market.dto.UserFilterDto;
import uz.isystem.market.exception.ServerBadRequestException;
import uz.isystem.market.user.userRole.UserRoleService;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    public UserDto get(Integer id){
        return convertEntityToDto(getEntity(id), new UserDto());
    }

    public User create(UserDto userDto){
        User user = convertDtoToEntity(userDto, new User());
        user.setStatus("Faol");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<UserDto> getAll(){
        List<User> userList = userRepository.findAllByDeletedAtIsNull();
        if(userList.isEmpty()) throw new ServerBadRequestException("User not found !");
        return userList.stream()
                .map(user -> convertEntityToDto(user, new UserDto()))
                .collect(Collectors.toList());
    }

    public User update(Integer id, UserDto userDto){
        User user = getEntity(id);
        user = convertDtoToEntity(userDto, user);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public String delete(Integer id){
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User successfully deleted !";
    }


//    <-------------   FILTERING   ------------->     //

    public List<User> filter(UserFilterDto filterDto){
        String sortBy = filterDto.getSortBy();
        if(sortBy == null){
            sortBy = "createdAt";
        }

        Pageable pageable =  PageRequest.of(filterDto.getPage(), filterDto.getSize(), filterDto.getDirection(), sortBy);
        List<Predicate> predicateList = new LinkedList<>();
        Specification<User> specification = ((root, query, criteriaBuilder) -> {
            if(filterDto.getName() != null){
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + filterDto.getName() + "%"));
            }
            if(filterDto.getSurname() != null){
                predicateList.add(criteriaBuilder.like(root.get("surname"), "%" + filterDto.getSurname() + "%"));
            }
            if(filterDto.getEmail() != null){
                predicateList.add(criteriaBuilder.like(root.get("subject"), "%" + filterDto.getEmail() + "%"));
            }
            if(filterDto.getContact() != null){
                predicateList.add(criteriaBuilder.like(root.get("contact"), "%" + filterDto.getContact() + "%"));
            }
            if(filterDto.getStartDate() != null && filterDto.getEndDate() != null){
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        filterDto.getStartDate(), filterDto.getEndDate()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });

        Page<User> certificatePage = userRepository.findAll(specification, pageable);
        return certificatePage.get().collect(Collectors.toList());
    }

    //     <-------------------------->     //


    // Secondary functions

    public User getEntity(Integer id){
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ServerBadRequestException("User not found !"));
    }

    public UserDto convertEntityToDto(User user, UserDto userDto){
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPassword(user.getPassword());
        userDto.setContact(user.getContact());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setImageId(user.getImageId());
        userDto.setUserRoleId(user.getUserRoleId());
        userDto.setUserRoleDto(userRoleService.get(user.getUserRoleId()));
        userDto.setStatus(user.getStatus());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setDeletedAt(user.getDeletedAt());

        return userDto;
    }

    public User convertDtoToEntity(UserDto userDto, User user){
//        return User.builder()
//                .name(userDto.getName())
//                .surname(userDto.getSurname())
//                .password(userDto.getPassword())
//                .contact(userDto.getContact())
//                .email(userDto.getEmail())
//                .address(userDto.getAddress())
//                .imageId(userDto.getImageId())
//                .userRoleId(userDto.getUserRoleId())
//                .build();

        user.setName(userDto.getName());
        user.setSurname(user.getSurname());
        user.setPassword(userDto.getPassword());
        user.setContact(userDto.getContact());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setImageId(userDto.getImageId());
        user.setUserRoleId(userDto.getUserRoleId());

        return user;
    }
}
