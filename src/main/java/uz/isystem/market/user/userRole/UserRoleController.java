package uz.isystem.market.user.userRole;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserRoleById(@PathVariable Integer id){
        return ResponseEntity.ok(userRoleService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createUserRole(@Valid @RequestBody UserRoleDto userRoleDto){
        return ResponseEntity.ok(userRoleService.create(userRoleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserRoleById(@PathVariable Integer id,
                                                @Valid @RequestBody UserRoleDto userRoleDto){
        return ResponseEntity.ok(userRoleService.update(id, userRoleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Integer id){
        return ResponseEntity.ok(userRoleService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUserRoles(){
        return ResponseEntity.ok(userRoleService.getAll());
    }
}
