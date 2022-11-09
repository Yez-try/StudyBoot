package com.iu.demo;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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
		
		//RestTemplete사용해보기 (spring에서 제공)
		RestTemplate restTemplate = new RestTemplate();
		
		//Header를 넣는 방법 (Header생성)
		HttpHeaders headers = new HttpHeaders();
		//Header도 key:value형식으로 이루어져있음
		//헤더에 값넣기 
		//1번방식
//		headers.set~~~로 되어있는 것은 이미 있는 헤더에 값을 넣는것이다. set헤더명("값");
		//2번방식, add로 하나씩 넣어주기
//		headers.add("key", "value");
		//3번방식, MultiValueMap으로 한꺼번에 넣어주기
//		headers.addAll(MultiValueMap<K, V>) 
		//파라미터 넣기
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("key", "value");
		
		//URL, Method를 포함한 요청 객체 생성 (header와 param을 모음)
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		//요청 전송 결과 처리 
		ResponseEntity<String> response = 
				restTemplate. //요청 메서드명ForEntity  : 만약 Post요청이라면 postForEntity
					getForEntity( //url, 반환받을 클래스 타입, request
						"https://jsonplaceholder.typicode.com/posts/1", 
						String.class, 
						request);
		
		//Post와 Get의 순서가 다르므로 주의
//		restTemplate.postForEntity("url", request, MemberVO.class);
		
		
		
		//내가 설정한 타입으로 Body를 반환해줌(이번에는 String으로 요청했기 때문에 String 타입으로 Body반환
		String result = response.getBody();
		
		log.info("response => {}", response);
		
		
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
	
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;
	
	@GetMapping(value = "/address")
	@ResponseBody
	public String address() throws Exception{
		//요청이 발생하면 특정 주소의 값을 검색해서 받아오기
		
		//kakao지도요청
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "KakaoAK "+"5a5c38c2f244a65daefe52887d1cb1fe");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("query", "전북 삼성동 100");
		
		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>( params, headers);
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://dapi.kakao.com/v2/local/search/address.json", String.class, req);
				
		return response.getBody();
  	}
}
