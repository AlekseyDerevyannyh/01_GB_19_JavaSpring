package com.gb.aspect;

import com.gb.TimerAspectProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TimerAspect {
    private final TimerAspectProperties properties;

    @Pointcut("within(@com.gb.annotation.Timer *)")
    public void beansAnnotatedWithTimer() {

    }

    @Pointcut("@annotation(com.gb.annotation.Timer)")
    public void methodsAnnotatedWithTimer() {

    }

    @Around("beansAnnotatedWithTimer() || methodsAnnotatedWithTimer()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = 0;
        if (properties.isMicroseconds()) {
            startTime = System.nanoTime();
        } else {
            startTime = System.currentTimeMillis();
        }

        Object result = joinPoint.proceed();

        long elapsedTime = 0;
        if (properties.isMicroseconds()) {
            elapsedTime = (System.nanoTime() - startTime) / 1000;
            log.info(joinPoint.getTarget().getClass().getName() + " - " + joinPoint.getSignature().getName() + " #" + elapsedTime + " mks");
        } else {
            elapsedTime = System.currentTimeMillis() - startTime;
            log.info(joinPoint.getTarget().getClass().getName() + " - " + joinPoint.getSignature().getName() + " #" + elapsedTime + " ms");
        }
        return result;
    }
}

