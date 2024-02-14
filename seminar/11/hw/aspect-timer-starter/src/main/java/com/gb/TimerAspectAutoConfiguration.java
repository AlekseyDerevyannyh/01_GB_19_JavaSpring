package com.gb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gb.aspect.TimerAspect;

@Configuration
@EnableConfigurationProperties(TimerAspectProperties.class)
@ConditionalOnProperty(value = "aspect.timer.enabled", havingValue = "true")
public class TimerAspectAutoConfiguration {

    @Bean
    TimerAspect timerAspect(TimerAspectProperties properties) {
        return new TimerAspect(properties);
    }
}
