package com.iu.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	//public을 선언하면 defalut로 바꾸라는 메세지가 출력됨
	WebSecurityCustomizer webSecurityConfig() {
		//Security에서 무시해야하는 URL패턴 등록
		return web -> web
				.ignoring()
				//아래 폴더 밑에 있는 건 무시해라
				.antMatchers("/images/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				.antMatchers("/favicon/**")
				.antMatchers("/resources/**");
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		//httpsecurity에 있는 cors랑 and랑......은 사용하지 맙시다 (일단아직 배우기 전이니까)
		httpSecurity
					.cors()
					.and()
					.csrf()
					.disable()
				.authorizeRequests()
					//인덱스페이지는 누구나한테 다 허용하겠다.
					.antMatchers("/").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers("/logout").permitAll()
					.antMatchers("/qna/list","/qna/detail").permitAll()
					.antMatchers("/qna/**").hasRole("MEMBER") //순서 중요!
					//루트밑에 admin은 admin롤을 가진 사람만 통과
					.antMatchers("/admin").hasRole("ADMIN") //DB의 권한이름과 동일해야함 ROLE_ADMIN이라서 ADMIN으로 맞춰야함 (ROLE_ 빼고)
					.antMatchers("/manager").hasRole("MANAGER")
					//user페이지는 로그인해야 볼수 있음 : HTTP ERROR 403 (권한이 없을때의 에러메세지)
//					.anyRequest().authenticated()
					.anyRequest().permitAll()
					.and() //위의 설정이 끝났다는 의미
				//로그인 폼 인증 설정
				.formLogin()
					.loginPage("/member/login") //내장된 로그인 폼을 사용하지 않고 개발자가 만든 폼요청 URL
//					.loginProcessingUrl(null) //로그인을 진행 요청할 form tag의 action의 주소를 지정
					.usernameParameter("id") //개발자가 username이 아닌 다른 이름으로 parameter를 사용할 때 이렇게 사용한다.
//					.passwordParameter("pw") 패스워드는 password로 동일하므로 나는 생략
					.defaultSuccessUrl("/")  //인증에 성공할 경우 요청할 URL
					.failureUrl("/member/login") //인증 실패할 경우 요청할 URL
					.permitAll();
		//이렇게 끊고가도 됨
		httpSecurity.logout()
					.permitAll(); //만약 anyRequest().permitAll()하면 나머지는 모두 허용
		
		return httpSecurity.build();
	}

}
