package uz.isystem.market.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.isystem.market.security.dto.AuthDto;
import uz.isystem.market.security.dto.RegistrationDto;
import uz.isystem.market.exception.ServerBadRequestException;
import uz.isystem.market.security.JwtTokenUtil;
import uz.isystem.market.user.User;
import uz.isystem.market.user.UserDto;
import uz.isystem.market.user.UserRepository;
import uz.isystem.market.user.userRole.UserRoleService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRoleService userRoleService;
    private final MailSenderService mailSenderService;

    @Value("${mailSendAddress}")
    private String address;

    public AuthService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, UserRoleService userRoleService, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRoleService = userRoleService;
        this.mailSenderService = mailSenderService;
    }

    public void register(RegistrationDto dto){
        if(!dto.getPassword().equals(dto.getCheckPassword()))
            throw new ServerBadRequestException("Password invalid !");

        Optional<User> optional = userRepository.findByContactAndEmail(dto.getContact(), dto.getEmail());
        if (optional.isPresent()){
            throw new ServerBadRequestException("User already exist");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(PasswordService.generateMD5(dto.getPassword()))
                .contact(dto.getContact())
                .createdAt(LocalDateTime.now())
                .status("Faol emas")
                .userRoleId(userRoleService.getEntityByName("ROLE_USER").getId())
                .build();

        userRepository.save(user);
        System.out.println("New user registred !");


//        For email
        String link = address + jwtTokenUtil.generateToken(user);
        String content = String.format("Please verify your data, click to link %s", link);

        try {
            mailSenderService.sendMail(content, dto.getEmail());
            System.out.println("Hello");
        } catch (Exception e) {
            e.printStackTrace();
            userRepository.delete(user);
        }
        System.out.println("aaaa");
    }

    public User verification(String token) {
        Optional<User> optional = userRepository.findById(Integer.valueOf(jwtTokenUtil.getId(token)));
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("Verification failed");
        }
        User user = optional.get();
        user.setStatus("Faol");
        userRepository.save(user);
        return user;
    }

    public UserDto auth(AuthDto dto) {
        String contact = dto.getContact();
        String password= PasswordService.generateMD5(dto.getPassword());
        Optional<User> optional = userRepository.authorize(password, contact);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("User not found");
        }
        User user = optional.get();
        UserDto userDto = new UserDto();
        userDto.setContact(userDto.getContact());
        userDto.setToken(jwtTokenUtil.generateToken(user));
        return userDto;
    }
}
