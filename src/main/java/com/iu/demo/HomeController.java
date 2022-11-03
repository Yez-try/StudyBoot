package com.iu.demo;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iu.demo.board.qna.QnaMapper;
import com.iu.demo.member.MemberVO;


@Controller
public class HomeController {
	@Value("${my.default}")
	private String app;

	@Autowired
	private QnaMapper qnaMapper;
	
	@Value("${my.message.hi}")
	private String message;
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class); //homeController내에 로그 기록을 찍겠다.
//	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "Admin Role";
	}
	
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "Manager Role";
	}
	
	@GetMapping("/user")
	@ResponseBody
	public String member() {
		return "Member Role";
	}
	
	@GetMapping("/")
	public String home(HttpSession session) throws Exception {	
		//스프링 시큐리티가 세션에 넣은 key값을 확인해 보자 : SPRING_SECURITY_CONTEXT
//		Enumeration<String> en = session.getAttributeNames();
//		
//		while(en.hasMoreElements()) {
//			String key = en.nextElement();
//			log.info("key => {}", key);
//		}
		
		SecurityContext context = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if(context!=null) {			
			log.info("obj => {}", ((MemberVO)(context.getAuthentication().getPrincipal())).getName());
		}
		
		log.info("========================================");
		log.info("message {}", message);
		log.info("default {}", app);
//		
//		List<QnaVO> ar = qnaMapper.getList();
//		
//		log.info("List : {} size {}", ar, ar.size()); //중괄호 안에 ar을 넣어라

		return "index";
	}
}
