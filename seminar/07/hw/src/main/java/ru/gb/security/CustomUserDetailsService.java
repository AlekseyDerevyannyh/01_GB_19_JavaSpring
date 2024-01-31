package ru.gb.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.gb.model.User user = userService.getUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getName(), user.getPassword(), List.of(
                new SimpleGrantedAuthority("admin"),
                new SimpleGrantedAuthority("reader")
        ));
    }
}
