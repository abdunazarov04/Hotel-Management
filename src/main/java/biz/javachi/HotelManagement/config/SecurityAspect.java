package biz.javachi.HotelManagement.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @After("@annotation(preAuthorize)")
    public void logPreAuthorize(JoinPoint joinPoint, PreAuthorize preAuthorize) {
        System.out.println("Method: " + joinPoint.getSignature().getName());
        System.out.println("PreAuthorize Expression: " + preAuthorize.value());
    }
}