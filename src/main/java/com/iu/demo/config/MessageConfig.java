package com.iu.demo.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer{

	//***-context.xml
	// <bean class="" id=""> == new 생성자
	
//	@Bean //이름을 따로 지정하지 않으면 Class의 이름을 소문자로 바꾼 것이 Bean의 이름이 된다.
	//여기서는 string이 Bean의 이름이다. 
	//(Bean은 클래슬 미리 생성해 놓는것이므로 여기서는 method가 <bean class="" id="">역할을 하고 즉, 메서드 명은 상관이 없다)
//	public String getString() {
//		return new String();
//	}
	
	@Bean
	public LocaleResolver locale() {
		//1. session에서 꺼내는 경우
		SessionLocaleResolver resolver = new SessionLocaleResolver(); 
		resolver.setDefaultLocale(Locale.KOREA);
		
		//2. Cookie를 사용하는 경우(보통 더 많이 사용)
		CookieLocaleResolver cResolver = new CookieLocaleResolver();
		
		//아무것도 지정안했을때 기본 위치 
		//해보니까 크롬설정의 언어에 먼저 영향을 받고 있음... 거기서 설정되어있으면 여기선 딱히 의미없음
		cResolver.setDefaultLocale(Locale.KOREAN);
		cResolver.setCookieName("lang");
		
		return cResolver;
	}
	
	//인터셉터 객체 만들기
	@Bean
	public LocaleChangeInterceptor changeInterceptor() {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("lang");
		//parameter를 받아서 언어를 구분하고, 그 정보를 lang이라는 쿠키 정보로 저장해줄것임
		//url?lang=ko : 한국어를 받겠다.
		return changeInterceptor;
	}
	

}
