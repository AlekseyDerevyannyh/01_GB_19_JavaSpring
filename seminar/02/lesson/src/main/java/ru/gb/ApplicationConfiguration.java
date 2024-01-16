package ru.gb;

import org.springframework.context.annotation.Bean;

// java-based configuration
//@Configuration
public class ApplicationConfiguration {

    @Bean
    UserRepository myUserRepository() {
        return new UserRepository();
    }

}
