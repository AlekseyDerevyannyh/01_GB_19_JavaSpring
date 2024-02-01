package ru.gb.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Role;
import ru.gb.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
//
//    @PostConstruct
//    public void generateData() {
//        roleRepository.save(new Role("admin"));
//        roleRepository.save(new Role("reader"));
//        roleRepository.save(new Role("user"));
//    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
