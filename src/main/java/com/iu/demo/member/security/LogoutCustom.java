package com.iu.demo.member.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.iu.demo.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogoutCustom implements LogoutHandler{

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		log.info("=====================LogotHandler=======================");

		
		request.getSession().invalidate();
	}
}
