package com.iu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.iu.demo.interceptors.StudyInterceptor;
import com.iu.demo.interceptors.TestInterceptor;

import lombok.extern.slf4j.Slf4j;

//Legacy에서 ***-context.xml 역할
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {
	// 	레거시의 servlet-context.xml <!-- Interceptor 객체 생성 -->
//		<beans:bean class="com.iu.start.interceptor.TestInterceptor" id="testInterceptor"></beans:bean>
//		
//		<!-- URL - Interceptor 맵핑 -->
//		<interceptors>
//			<interceptor>
//				<!-- mapping과 beans의 순서를 꼭 지켜줄 것 -->
//				<!-- 루트로 시작하는 모든 경로를 interceptor를 거치도록 한다. -->
//				<mapping path="/**"/>
//				<!-- testInterceptor 객체를 참조해라 -->
//				<beans:ref bean="testInterceptor"/>
//			</interceptor>
//			<interceptor>
//				<mapping path="/qna/*"/>
//				<mapping path="/member/myPage.mg"/>
//				<!-- exclude-mapping의 주소는 제외하고 -->
//				<exclude-mapping path="/qna/list.mg"/>
//				<beans:bean class="com.iu.start.interceptor.memberInterceptor" id="memberInterceptor"></beans:bean>
//			</interceptor>
//			<interceptor>
//				<mapping path="/qna/update.mg"/>
//				<mapping path="/notice/update.mg"/>
//				<beans:bean class="com.iu.start.interceptor.WriterCheckInterceptor" id="writerCheckInterceptor"></beans:bean>
//			</interceptor>
//			
//		</interceptors>
	@Autowired  //IOC(Inversion Of Controll)
	private TestInterceptor testInterceptor;
	@Autowired
	private StudyInterceptor studyInterceptor;
	
	@Override //상속받은 메서드를 재정의 : 오버로딩-같은이름의 메서드를 여러개 만드는것
	public void addInterceptors(InterceptorRegistry registry) {
		//registry 에 등록한다.
		registry.addInterceptor(testInterceptor)
		//인터셉터를 적용할 URL 등록
				.addPathPatterns("/qna/**")
		//제외할 URL 등록
				.excludePathPatterns("/qna/list")
				.addPathPatterns("/notice/**"); //method chaining : 메서드를 끊지않고, 계속 점으로 연결하는 방식
		
		//registry 에 등록한다.
		registry.addInterceptor(studyInterceptor)
				.addPathPatterns("/qna/**")
				.excludePathPatterns("/qna/detail");
		
		// 같은 url(qna/write)의 testInterceptor와 studyInterceptor 중 무엇을 먼저 실행할까?
		// Interceptor 순서 : config class 에 등록된 순서대로 적용한다.
	
	}
}
