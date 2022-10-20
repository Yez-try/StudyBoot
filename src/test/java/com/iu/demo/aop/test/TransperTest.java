package com.iu.demo.aop.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransperTest {
	@Autowired
	private Transport transport;
	@Autowired
	private Card card;
	
	@Test
	void test() {
		transport.airplane();
	}
}
