package uz.isystem.market.user.userRole;

import org.springframework.stereotype.Service;
import uz.isystem.market.exception.ServerBadRequestException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleDto get(Integer id){
        return convertEntityToDto(getEntity(id), new UserRoleDto());
    }

    public UserRole create(UserRoleDto userRoleDto){
        UserRole userRole = convertDtoToEntity(userRoleDto, new UserRole());
        userRole.setStatus("Faol");
        userRole.setCreatedAt(LocalDateTime.now());
        return userRoleRepository.save(userRole);
    }

    public List<UserRoleDto> getAll(){
        List<UserRole> userRoleList = userRoleRepository.findAllByDeletedAtIsNull();
        if(userRoleList.isEmpty()){
            throw new IllegalArgumentException("User Role not found !");
        }
        return userRoleList.stream()
                .map(userRole -> convertEntityToDto(userRole, new UserRoleDto()))
                .collect(Collectors.toList());
    }

    public UserRole update(Integer id, UserRoleDto userRoleDto){
        // Checking...
        UserRole userRole = getEntity(id);

        convertDtoToEntity(userRoleDto, userRole);
//        userRole.setId(id);
        userRole.setUpdatedAt(LocalDateTime.now());
        return userRoleRepository.save(userRole);
    }

    public String delete(Integer id){
        UserRole userRole = getEntity(id);
        userRole.setDeletedAt(LocalDateTime.now());
        userRoleRepository.save(userRole);
        return "User role successfully deleted !";
    }


    // Secondary functions

    public UserRole getEntity(Integer id){
        return  userRoleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ServerBadRequestException("User Role not found !"));
    }

    public UserRoleDto convertEntityToDto(UserRole userRole, UserRoleDto userRoleDto){
        userRoleDto.setId(userRole.getId());
        userRoleDto.setName(userRole.getName());
        userRoleDto.setStatus(userRole.getStatus());
        userRoleDto.setCreatedAt(userRole.getCreatedAt());
        userRoleDto.setDeletedAt(userRole.getDeletedAt());
        userRoleDto.setUpdatedAt(userRole.getUpdatedAt());
        return userRoleDto;
    }

    public UserRole convertDtoToEntity(UserRoleDto userRoleDto, UserRole userRole){
        userRole.setName(userRoleDto.getName());
        return userRole;
    }

    public UserRole getEntityByName(String name) {
        List<UserRole> optional = userRoleRepository.findByNameAndDeletedAtIsNull(name);
        if (optional.isEmpty()){
            throw new ServerBadRequestException("Usertype not found");
        }
        return optional.get(0);
    }
}
