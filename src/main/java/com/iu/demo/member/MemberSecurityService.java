package com.iu.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberSecurityService implements UserDetailsService {

	@Autowired
	private MemberDAO dao;
	
	//유저네임을 넣으면 userDetails타입을 리턴한다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("========로그인 시도 중=========");
		MemberVO memberVO = dao.getLogin(username);
		log.info("memberVO {}", memberVO);
		
		
		return memberVO;
	}
}
