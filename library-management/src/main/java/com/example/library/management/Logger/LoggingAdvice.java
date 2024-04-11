package com.example.library.management.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Aspect
@Component
public class LoggingAdvice {
Logger log= LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut("execution(* com.example.library.management.*.*.*(..)) && !within(com.example.library.management.Security.*)")
    public void pointCut(){

}
    @Around("pointCut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
    ObjectMapper mapper = new ObjectMapper();
    String className = joinPoint.getTarget().getClass().toString();
    String methodName = joinPoint.getSignature().getName();
    Object[] array = joinPoint.getArgs();
    log.info("method invoked {} : {}()arguments : {}",className , methodName, mapper.writeValueAsString(array));
    Object object = joinPoint.proceed();
    log.info("method invoked {} : {}()Response : {}", className, methodName, mapper.writeValueAsString(object));
    return object;

}

}
