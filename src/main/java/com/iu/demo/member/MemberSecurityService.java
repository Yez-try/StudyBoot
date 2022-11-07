package com.iu.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// spring security 설정 implements UserDetailsService 인터페이스 상속
//소셜로그인 extends DefaultOAuth2UserService 추상클래스를 상속 받아준다.
@Service
@Slf4j
public class MemberSecurityService extends DefaultOAuth2UserService implements UserDetailsService {

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
	
	
	//소셜로그인 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("================== social Login 시도 ==================");
		//getClientRegistration 을 호출하면, 
		//{registrationId='kakao', 
		//clientId='properties의 clientID', 
		//clientSecret='properties의 secret', 
		//clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@2590a0, 
		//authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, 
		//redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', 
		//scopes=[profile_nickname, account_email, birthday], 
		//providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@168afccc, 
		//clientName='Kakao'} clientName이 가장 중요 이걸로 카카오인지 네이버인지 구분
		log.info("userRequest : AccessToken {}",userRequest.getAccessToken().getTokenValue());
		log.info("userRequest : getClientRegistration {}",userRequest.getClientRegistration());
		log.info("userRequest : getAdditionalParameters {}",userRequest.getAdditionalParameters());
		log.info("userRequest : getProviderDetails {}",userRequest.getClientRegistration().getProviderDetails().getConfigurationMetadata());
		
		log.info("-------------------사용자 정보-----------------");
		OAuth2User auth2User = super.loadUser(userRequest);
		log.info("OAuth2User : getName   {} ", auth2User.getName()); //사용자의 고유id
		log.info("Attr => {}", auth2User.getAttributes()); //connected 연결일시, nickname, 프로필이미지등 필요한 정보를 꺼낼 수 있음.
		log.info("Auth => {}", auth2User.getAuthorities()); //ROLE_USER는 카카오의 권한 등급을 의미 , 우리가 쓰는거랑 다른거임
		
		
		//처음이면 회원가입 필요
		//두번째면 로그인 필요
		return auth2User;
	}
	
	private OAuth2User socialJoinCheck(OAuth2UserRequest userRequest) throws Exception{
		return null;
	}

}
