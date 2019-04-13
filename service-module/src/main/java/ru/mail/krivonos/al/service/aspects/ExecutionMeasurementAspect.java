package ru.mail.krivonos.al.service.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ExecutionMeasurementAspect {

    private static final Logger logger = LogManager.getLogger(ExecutionMeasurementAspect.class);

    @Pointcut("execution(* ru.mail.krivonos.al..*.*(..))")
    public void callAtEveryMethod() {
    }

    @Around("callAtEveryMethod()")
    public Object aroundEveryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long executionTime = end - start;
        logger.debug(String.format("%s started %s", joinPoint.getSignature(), new Date(start)));
        logger.debug(String.format("%s ended %s", joinPoint.getSignature(), new Date(end)));
        logger.debug(String.format("%s executed for %d millis.", joinPoint.getSignature(), executionTime));
        return proceed;
    }
}
