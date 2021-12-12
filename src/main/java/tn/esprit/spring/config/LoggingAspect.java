package tn.esprit.spring.config;

import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	
private static final org.apache.logging.log4j.Logger l = LogManager.getLogger(LoggingAspect.class);

@After("execution(* tn.esprit.spring.services.stock.StkServiceImpl.*(..))")
public void logMethodEntry(JoinPoint joinPoint) {
String name = joinPoint.getSignature().getName();
l.info("**********************************************************************************In method " + name + " : ");
}

@Before("execution(* tn.esprit.spring.services.stock.StkServiceImpl.*(..))")
public void logMethodEntr(JoinPoint joinPoint) {
String name = joinPoint.getSignature().getName();
l.info("---------------------- " + name + " : ");
}

@Around("execution(* tn.esprit.spring.services.stock.StkServiceImpl.*(..))")
public Object profile(ProceedingJoinPoint pjp) throws Throwable {
long start = System.currentTimeMillis();
Object obj = pjp.proceed();
long elapsedTime = System.currentTimeMillis() - start;
l.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Method execution time: " + elapsedTime + " milliseconds.");
return obj;
}

}