package com.gb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("aspect.timer")
public class TimerAspectProperties {
    /**
     * Включить измерение в микросекундах вместо миллисекунд
     * false - миллисекунды
     * true - микросекунды
     */
    private boolean microseconds = false;
}
