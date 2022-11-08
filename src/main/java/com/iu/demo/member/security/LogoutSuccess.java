package com.iu.demo.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.iu.demo.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogoutSuccess implements LogoutSuccessHandler{

	@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			//? 일반? 아니면 소셜로그아웃??
			log.info("Auth => {}", authentication);
			
			MemberVO memberVO = (MemberVO)authentication.getPrincipal();
			String social = memberVO.getSocial();
			if(social != null && social.equals("kakao")) {
				//카카오 로그아웃
				try {
					//메서드가 끝나면 해당주소로 리다이렉트를 보내라
					response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id=8916825993163e8fc4e9bc892c9224ce&logout_redirect_uri=http://localhost:81/member/logoutResult");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(social != null && social.equals("google")) {
				//구글 로그아웃
				
			}else { 
				//일반로그아웃
				response.sendRedirect("/");
			}
			
		}
}
