package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

//		UserRepository userRepository = (UserRepository) context.getBean("myUserRepository");
//		UserRepository userRepository11 = context.getBean("myUserRepositoryBean", UserRepository.class);

//		UserRepository userRepository1 = context.getBean(UserRepository.class);
//		UserRepository userRepository2 = context.getBean(UserRepository.class);
//		UserRepository userRepository3 = context.getBean(UserRepository.class);
//
//		System.out.println(userRepository == userRepository1);
//
//		System.out.println(userRepository1 == userRepository2);
//		System.out.println(userRepository1 == userRepository3);
//		System.out.println(userRepository2 == userRepository3);

//		UserRepository userRepository = new UserRepository();
//		System.out.println(userRepository.getAll());
//		System.out.println(userRepository.getById(2));
    }

}
