package com.iu.demo.aop.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class Card {
	
	@Before("execution(* com.iu.demo.aop.test.Transport.airplane())") //get으로 시작하는 모든 메서드
	public void before() {
		log.info("--확인--");
	}

	@After("execution(* com.iu.demo.aop.test.Transport.get*())") //get으로 시작하는 모든 메서드
	public void cardCheck() {
		log.info("--삐빅--");
	}
	
	//around 는 after+before
	@Around("execution(* com.iu.demo.aop.test.Transport.take*())") //get으로 시작하는 모든 메서드
	public Object cardCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		log.info("--삑 승차입니다.--");
		Object object = joinPoint.proceed();
		log.info("--삑 하차입니다.--");
		return object;
	}
	

}
