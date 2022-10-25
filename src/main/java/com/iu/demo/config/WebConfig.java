package com.iu.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

//설정 파일 WebMvcConfigurer 를 상속해준다. ***-context.xml역할
//@Component //객체를 만들고 실행은 안함. 필요할때 주입시켜서 사용
@Configuration //설정 파일을 만드는 객체
@Slf4j
public class WebConfig implements WebMvcConfigurer{ //mvc란? 디자인 패턴, model and view controller
	
	@Value("${my.upload.base}") //스프링에서의 EL : SPEL : 이라고 부름.
	private String filePath;
	@Value("${my.url.path}")
	private String urlPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		log.info("========================");
		log.info("filePath {}", filePath);
		log.info("urlPath {}", urlPath);
		
		//추가할 것을 등록한다.
		//서블릿콘텍스트의 <resources mapping="/resources/**" location="/resources/" />
		registry.addResourceHandler(urlPath) //요청 URL 주소
				.addResourceLocations(filePath);

	}

}
