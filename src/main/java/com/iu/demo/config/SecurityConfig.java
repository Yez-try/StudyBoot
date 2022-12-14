package com.iu.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.iu.demo.member.MemberSecurityService;
import com.iu.demo.member.security.LoginFail;
import com.iu.demo.member.security.LoginSuccess;
import com.iu.demo.member.security.LogoutCustom;
import com.iu.demo.member.security.LogoutSuccess;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private LoginSuccess loginSuccess;
	@Autowired
	private LoginFail loginFail;
	@Autowired
	private MemberSecurityService memberSecurityService;
	@Autowired
	private  LogoutCustom logoutCustom;
	@Autowired
	private LogoutSuccess logoutSuccessHandler;
	
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
					//csrf활성화는 아래 csrf()와 disable()을 삭제한다
					//활성화 되면 요청 전송시 CSRF토큰을 적용시켜야 한다.
					//로그아웃시 문제 발생 : 이유) logout경로를 get으로 보냈지만, security내부적으로 실제론 POST로 처리함
						//로그아웃 할때, form태그로 제출하게하여 토큰도 함께 보내주도록한다.
//					.csrf()
//					.disable()
					.cors()
					.configurationSource(this.corsConfigurationSource())
					.and()
				.authorizeRequests()
					//인덱스페이지는 누구나한테 다 허용하겠다.
					.antMatchers("/").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers("/logout").permitAll()
					.antMatchers("/qna/list","/qna/detail").permitAll()
//					.antMatchers("/qna/**").hasRole("MEMBER") //순서 중요!
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
//					.loginProcessingUrl("/member/add") //로그인을 진행 요청할 form tag의 action의 주소를 지정
					.usernameParameter("id") //개발자가 username이 아닌 다른 이름으로 parameter를 사용할 때 이렇게 사용한다.
//					.passwordParameter("pw") 패스워드는 password로 동일하므로 나는 생략
//					.defaultSuccessUrl("/")  //인증에 성공할 경우 요청할 URL (successHandler가 있으면 거기서 실행해도 된다)
//					.failureUrl("/member/login?error=true&message=LoginFail") //인증 실패할 경우 요청할 URL (기본 값은 로그인 페이지?error)
					.failureHandler(loginFail)
					.permitAll()
					.successHandler(loginSuccess); //AuthenticationSuccessHandler를 impl받은 클래스를 생성해 넣어준다.
		//이렇게 끊고가도 됨
		httpSecurity.logout()
						.logoutUrl("/out")
//						.logoutSuccessUrl("/")	
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.addLogoutHandler(logoutCustom)
						.logoutSuccessHandler(logoutSuccessHandler) //로그아웃 성공하면 가는곳
						.permitAll() //만약 anyRequest().permitAll()하면 나머지는 모두 허용
						.and()
					.rememberMe()//RememberMe 설정
						.rememberMeParameter("rememberMe") //Defalut는 remember-me
						.tokenValiditySeconds(300) //로그인 유지시간 (초단위)
						.key("rememberMe") //key는 인증받은 사용자의 정보로 Token 생성시 필요, 필수
						.userDetailsService(memberSecurityService)  //인증 절차를 실행할 UserDetailservice, 필수
						.authenticationSuccessHandler(loginSuccess) //로그인 성공했을때 어디로 갈거니???
						.and()
					//소셜로그인 설정을 실행하겠다.
					.oauth2Login()
						.userInfoEndpoint()
						.userService(memberSecurityService) //loadUser 메서드가 필요함.
						;
		return httpSecurity.build();
	}
	
	//평문(Clear Text)를 암호화 시켜주는 객체 생성
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(); //이클래스의 객체가 평문을 암호화 해
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//아래 출처는 허락하겠다.
		configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500","*"));//"*" 모든 URL을 다 허용하겠다. 라는 뜻
		//아래 메서드 방식은 허락하겠다.
		configuration.setAllowedMethods(Arrays.asList("GET","POST")); 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//우리 서버의 어떤 url에 적용시킬거냐(뭘 허락해줄거냥
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}

}
