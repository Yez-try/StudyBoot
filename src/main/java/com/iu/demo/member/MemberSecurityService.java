package com.iu.demo.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.vote.RoleVoter;
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
		

		//kakao인지 네이버인지 구글인지에 따라 , 형식이 조금씩 다르므로 각각 메서드를 만든다.
		String social = userRequest.getClientRegistration().getRegistrationId();
		log.info("social의 이름은 뭐냐 {}",social);
		
		OAuth2User auth2User = this.socialJoinCheck(userRequest);
		
		//처음이면 회원가입 필요
		//두번째면 로그인 필요
		return auth2User;
	}
	
	//회원가입 유무를 판별한다.
	private OAuth2User socialJoinCheck(OAuth2UserRequest userRequest){
		log.info("-------------------사용자 정보-----------------");
		OAuth2User auth2User = super.loadUser(userRequest);
		log.info("OAuth2User : getName   {} ", auth2User.getName()); //사용자의 고유id
		log.info("Attr => {}", auth2User.getAttributes()); //connected 연결일시, nickname, 프로필이미지등 필요한 정보를 꺼낼 수 있음.
		log.info("Auth => {}", auth2User.getAuthorities()); //ROLE_USER는 카카오의 권한 등급을 의미 , 우리가 쓰는거랑 다른거임
		
		Map<String, Object> map = auth2User.getAttributes();
		
		//map for문 돌리려면 어떻게 할래?
		//1. 키 꺼내기 (열거형으로)
		Iterator<String> keys = map.keySet().iterator();
		//2. 몇갠지 모르면 for말고 while을 쓴다. 
		while(keys.hasNext()) {
			String key = keys.next();
			log.info("key : {}", key);
		}
		//properties를 꺼내쓰려고 하는데 무슨 타입일까?
		log.info("ClassName : {}", auth2User.getAttribute("properties").getClass().toString());
		
		LinkedHashMap<String, String> lm = auth2User.getAttribute("properties");
		Map<String, Object> ka = auth2User.getAttribute("kakao_account");
		
		MemberVO memberVO = new MemberVO();
		memberVO.setId(auth2User.getName()); // ID
		//PW가 없으므로, 비워두거나, 랜덤한 값으로 생성
//		memberVO.setPassword(null);
		memberVO.setName(lm.get("nickname"));
		memberVO.setEmail(ka.get("email").toString());
		memberVO.setSocial(userRequest.getClientRegistration().getRegistrationId());
		
		memberVO.setAttributes(auth2User.getAttributes());
		
		
		//Role을 지금은 잠시 임의로 만들어 넣어주자. (나중에 DB 에 넣고, 할거다.)
		List<MemberRoleVO> list = new ArrayList<>();
		MemberRoleVO roleVO = new MemberRoleVO();
		roleVO.setRoleName("ROLE_MEMBER");
		list.add(roleVO);
		
		memberVO.setMemberRoleVOs(list);
		
		return memberVO;
	}

}
