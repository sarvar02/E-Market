package uz.isystem.market.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.isystem.market.user.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private final Integer id;
    private final String email;
    private final String password;
    private final String status;
    private final String name;
    private final String surname;
    private final List<GrantedAuthority> authorityList;

    public CustomUserDetail(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.authorityList = List.of(new SimpleGrantedAuthority(user.getUserRole().getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
