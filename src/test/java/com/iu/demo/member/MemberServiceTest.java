package com.iu.demo.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

	@Autowired
	private MemberDAO dao;
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	void test() throws Exception {
		MemberVO memberVO = new MemberVO();
		memberVO.setId("manager1");
		memberVO.setPassword(encoder.encode("manager1"));
		memberVO.setName("manager1");
		memberVO.setEmail("manager1@gmail.com");
		
		int result = dao.setJoin(memberVO);
		assertEquals(1, result);
	}

}
