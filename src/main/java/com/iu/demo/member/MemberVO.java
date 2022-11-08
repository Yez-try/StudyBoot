package com.iu.demo.member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

//멤버VO는 UserDetails와 OAuth2User를 구현했다! 라고 말함
@Data
public class MemberVO implements UserDetails, OAuth2User{

	
	@NotBlank
	private String id;
	@Size(max=10, min=4)
	private String password;
	@Size(max=10, min=4, message = "비밀번호는 같아야해")
	private String pwCheck;
	@NotBlank
	private String name;
	@Email
	private String email;
	@Range(max = 150, min = 0)
	private int age;
	@Past
	private Date birth; //form:input을 사용하면 문자열이어도 데이트 타입으로 들어간다
	private Boolean enabled;
	private List<MemberRoleVO> memberRoleVOs;
	
	//OAuth2User, Token 등 정보를 저장
	private Map<String, Object> attributes;
	
	//Social Login (naver니?, kakao니? Google이니?)
	private String social;
	
	//------OAuth2User Override-------------
	//attributes의 getter역할
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return this.attributes;
	}
	
	
	//===========UserDetails Override===========
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ?는 any 를 뜻함, extends GrantedAuthority 를 상속받는 아무타입이면 된다.
		// (+) <? super T> T나 T의 부모타입을 허용하겠다 라는 뜻
		
		//Collection이 List의 부모타입이므로 이렇게 가능
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(MemberRoleVO roleVO: memberRoleVOs) {
			//꺼낸 rolename을 authorities에 담으려고 한다.
			authorities.add(new SimpleGrantedAuthority(roleVO.getRoleName()));
		}
		
		return authorities;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public boolean isAccountNonExpired() {
		// 사용자 계정의 만료 여부
		// true : 만료가 되지 않았습니다.
		// false : 만료 됨. 로그인 불가 (DB 컬럼으로 0,1)구분
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		// 계정 잠김 여부
		// 예를 들면 휴가간 사람의 계정을 잠근다거나 할 수 있음
		// true : 계정이 잠기지 않음
		// false : 계정이 잠김, 로그인이 불가
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		//비밀번호 만료여부
		//true : 만료안됨
		//false : 만료됨, 로그인 안됨
		return true;
	}
	@Override
	public boolean isEnabled() {
		// 계정 사용 여부
		// true : 계정 활성화(계정 사용 가능)
		// false : 계정 비활성화
		return true;
	}
	

	
}
