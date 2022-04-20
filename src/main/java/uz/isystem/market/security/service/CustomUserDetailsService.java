package uz.isystem.market.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.isystem.market.security.CustomUserDetail;
import uz.isystem.market.user.User;
import uz.isystem.market.user.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByContactAndDeletedAtIsNull(contact);
        if (optional.isEmpty()){
            throw new UsernameNotFoundException("Phone number not found");
        }
        return new CustomUserDetail(optional.get());
    }
}
