package com.iu.demo.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	
	public int setJoin(MemberVO memberVO) throws Exception;
	// ROLES의 NUM (ADMIN : 1, MANAGER : 2, MEMBER : 3)
	public int setRole(MemberRoleVO memberRoleVO) throws Exception;
	public MemberVO getLogin(MemberVO memberVO) throws Exception;
	public int checkID(MemberVO memberVO) throws Exception;

}
