package com.iu.demo.member;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class MemberVO {

	private String id;
	private String password;
	private String name;
	private String email;
	private Boolean enabled;
	private List<MemberRoleVO> memberRoleVOs;
}
