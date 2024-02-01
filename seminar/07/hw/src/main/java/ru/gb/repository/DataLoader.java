package ru.gb.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.gb.model.Role;
import ru.gb.model.User;
import ru.gb.service.RoleService;
import ru.gb.service.UserService;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleService.addRole(new Role("admin"));
        roleService.addRole(new Role("reader"));
        roleService.addRole(new Role("user"));

        userService.addUser(new User("admin", "admin"));
        userService.addUser(new User("reader", "reader"));
        userService.addUser(new User("user", "user"));

        userService.addRoleToUser(1L, 1L);
        userService.addRoleToUser(2L, 2L);
        userService.addRoleToUser(3L, 3L);
    }
}
