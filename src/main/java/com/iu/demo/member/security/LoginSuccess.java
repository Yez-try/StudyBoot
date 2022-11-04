package com.iu.demo.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccess implements AuthenticationSuccessHandler{

	//DS들어오기 전임.
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("====onAuthenticationSuccess=로그인 성공하면 실행=========");
		log.info("Auth => {}", authentication);
		log.info("ID : {}", request.getParameter("id"));
		//아이디 기억하기 (check 안하면 null, check 하면 on)
		log.info("remember {}", request.getParameter("remember"));
		String remember = request.getParameter("remember");
		
		//remember.equals("on")로 하면 nullpointException이 발생할 수 있다.
		if(remember!=null) {
			//id를 쿠키에 담아 보내보자
			Cookie cookie = new Cookie("userId", request.getParameter("id"));
			//쿠키 유효시간 설정 60초
			cookie.setMaxAge(60);
			//쿠키를 어느 도메인에서 사용가능하니? 같은 도메인내에서 어느 URL에서 사용가능하니?
			cookie.setPath("/");
			cookie.setHttpOnly(true); //쿠키를 클라이언트에서 숨길거니?
			response.addCookie(cookie);

			log.info("쿠키에 userId를 담았어!");
			
		}else {
			Cookie[] cookies = request.getCookies();
			for(Cookie ck : cookies) {
				if(ck.getName().equals("userId")) {
					ck.setMaxAge(0);
					ck.setPath("/"); //쿠키 삭제시 cookie를 만들때의 path와 동일해야만 지울 수 있다. path가 다르면 안지워진다.
					response.addCookie(ck);
				}
				log.info("쿠키~~{}~{}",ck.getName(), ck.getValue());
			}
		}
		
		response.sendRedirect("/"); //홈으로 보내겠다.
	}
	
}
