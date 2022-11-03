package com.iu.demo.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Mapper
public interface MemberDAO {
	
	public int setJoin(MemberVO memberVO) throws Exception;
	// ROLES의 NUM (ADMIN : 1, MANAGER : 2, MEMBER : 3)
	public int setRole(MemberRoleVO memberRoleVO) throws Exception;
	public MemberVO getLogin(String username) throws UsernameNotFoundException;
	public int checkID(MemberVO memberVO) throws Exception;

}
