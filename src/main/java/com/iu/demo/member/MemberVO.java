package com.iu.demo.member;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class MemberVO {

	@NotBlank
	private String id;
	@Size(max=10, min=4)
	private String password;
	@Size(max=10, min=4, message = "비밀번호는 같아야해")
	private String pwCheck;
	private String name;
	@Email
	private String email;
	@Range(max = 150, min = 0)
	private int age;
	@Past
	private Date birth; //form:input을 사용하면 문자열이어도 데이트 타입으로 들어간다
	private Boolean enabled;
	private List<MemberRoleVO> memberRoleVOs;
}
