package uz.isystem.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.market.security.dto.AuthDto;
import uz.isystem.market.security.dto.RegistrationDto;
import uz.isystem.market.security.service.AuthService;
import uz.isystem.market.user.User;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthDto authDto){
        return ResponseEntity.ok(authService.auth(authDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto){
        authService.register(registrationDto);
        return ResponseEntity.ok("Check your email, we have sent verification to your email");
    }

    @GetMapping("/validation/{token}")
    public ResponseEntity<?> validate(@PathVariable("token") String token){
        User user = authService.verification(token);
        return ResponseEntity.ok(user);
    }
}
