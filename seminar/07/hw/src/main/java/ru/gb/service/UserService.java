package ru.gb.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.User;
import ru.gb.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void generateData() {
        userRepository.save(new User("user", "user"));
        userRepository.save(new User("admin", "admin"));
        userRepository.save(new User("reader", "reader"));
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
