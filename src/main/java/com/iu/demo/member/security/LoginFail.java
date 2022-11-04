package com.iu.demo.member.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginFail implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("-------LoginFail--------");
//		log.info("Exception   {} ", exception);
		log.info("LocalMessage => {}", exception.getLocalizedMessage());
		log.info("ClassName => {}", exception.getClass().toString());
		log.info("Message => {}", exception.getMessage());
		
		String name = exception.getClass().toString();
		name = name.substring(name.lastIndexOf(".")+1);
		if(name.equals("BadCredentialsException")) {
			name = "비밀번호 틀림";
		}
		//redirect
//		name = URLEncoder.encode(name, "UTF-8"); //파라미터가 인코딩 문제가 생기면 이렇게
//		response.sendRedirect("/member/login?error=true&message="+name);
		
		
		//jsp로 바로 보내보기
		request.setAttribute("msg",name);
		request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response); 
		
		//POST방식으로 Controlller의 메서드를 요청함
//		 request.getRequestDispatcher("/member/login"); //post method에서 처리
		
	}
}
