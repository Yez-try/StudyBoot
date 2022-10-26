package com.iu.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.iu.demo.board.qna.QnaVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MemberAdvice {
	
	@After("execution(* com.iu.demo.member.MemberService.get*(..))")
	public void beforeTest(JoinPoint joinpoint) {
		log.info("-------------------After---------------");
		log.info("Args : {}", joinpoint.getArgs());
		log.info("Kind : {}", joinpoint.getKind());
		
	}
	

	@Around("execution(* com.iu.demo.member.MemberService.set*(..))")
	public Object aroundTest(ProceedingJoinPoint joinPoint/*setWrite를 실행시키면, joinPoint로 만들어서 가져와서 여기서 실행됨*/)throws Throwable{
		log.info("--------------------before---------------------");
		//point-cut의 클래스 객체 : point-cut이란 핵심로직으로 여기서는 qnaservice의 set*()메서드를 의미한다.
		log.info("Target : {}",joinPoint.getTarget());
		//point-cut의 클래스 객체
		log.info("This : {}",joinPoint.getThis());
		//point-cut으로 전달되는 매개변수의 인자값
		log.info("Args : {}",joinPoint.getArgs());
		Object[] objs = joinPoint.getArgs();
		QnaVO qnaVO = (QnaVO)objs[0]; //다형성
		
		Object obj = joinPoint.proceed();
		//트랜잭션 롤백 처리하기
		//필요한 서비스에 @Transactional(rollbackFor = Exception.class)어노테이션 주기 (메서드 혹은 클래스)

		log.info("--------------------after--------------------");
		log.info("obj: {}",obj);
		
		return obj;
	}
}
