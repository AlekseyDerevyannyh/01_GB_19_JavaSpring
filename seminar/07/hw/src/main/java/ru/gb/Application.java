package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gb.model.Role;
import ru.gb.model.User;
import ru.gb.service.RoleService;
import ru.gb.service.UserService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        UserService userService = SpringApplication.run(Application.class, args).getBean(UserService.class);
//        RoleService roleService = SpringApplication.run(Application.class, args).getBean(RoleService.class);
//
//        roleService.addRole(new Role("admin"));
//        roleService.addRole(new Role("reader"));
//        roleService.addRole(new Role("user"));
//
//        userService.addUser(new User("admin", "admin"));
//        userService.addUser(new User("reader", "reader"));
//        userService.addUser(new User("user", "user"));
//
//        userService.addRoleToUser(1L, 1L);
    }
}
