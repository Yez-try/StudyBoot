package com.iu.demo.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.iu.demo.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogoutSuccess implements LogoutSuccessHandler{
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;

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

				//메서드가 끝나면 해당주소로 리다이렉트를 보내라
				response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id="+clientId+"&logout_redirect_uri=http://localhost:81/member/logoutResult");

				
				//바로 로그아웃은 안되나? 테스트 (실패)
//				response.sendRedirect("https://developers.kakao.com/logout");
//				RestTemplate restTemplate = new RestTemplate();
				//header x, parameter x
//				
				
//				ResponseEntity<String> respEntity = restTemplate.getForEntity("https://developers.kakao.com/logout", String.class);
//				log.info("respEntity {}", respEntity);
//				response.sendRedirect("/");

			}else if(social != null && social.equals("google")) {
				//구글 로그아웃
				
			}else { 
				//일반로그아웃
				response.sendRedirect("/");
			}
			
		}
}
