package com.iu.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	@Value(value = "${social.kakao.admin}")
	private String adminKey;
	
	public int setDelete(MemberVO memberVO) throws Exception{
		
		//webClient 생성
		WebClient client = WebClient.builder()
									.baseUrl("https://kapi.kakao.com/")
									.build();
		
		//parameter 전달, map은 안됨!
		MultiValueMap<String, String> map = new LinkedMultiValueMap();
		map.add("target_id_type", "user_id");
		map.add("target_id", memberVO.getId());
		
		Mono<String> res =  client.post()
									.uri("v1/user/unlink")
									.bodyValue(map)
									.header("Authorization", "KakaoAK "+adminKey)
									.header("Content-Type", "application/x-www-form-urlencoded")
									.retrieve()
									.bodyToMono(String.class);
		
		log.info("res.block() => {}", res.block());
		
		return 1;
	}
	
	public int setDelete2(MemberVO memberVO) throws Exception{
		//요청하자
		RestTemplate restTemplate = new RestTemplate();
		
		//Header
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "KakaoAK "+adminKey);
		
		//parameter
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		params.add("target_id_type", "user_id");
		params.add("target_id", memberVO.getId());
		
		//요청객체
		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);
		
		ResponseEntity<String> res = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/unlink", req, String.class);
		
		log.info("response {}", res.getBody());
		
		int result = 0;
		
		if(res.getBody()!=null) {
			result = 1;
		}
		return result;
		
	}
	
	//사용자 정의 검증 메서드
	public boolean getMemberError(MemberVO memberVO, BindingResult bindingResult)throws Exception{
		boolean check=false;
		// check=false : 검증성공(error 없음)
		// check=true : 검증실패(error 있음)
		
		//1. annotation검증 : true면 에러 있음
		check = bindingResult.hasErrors();
		
		//2. password가 일치하는지 검증
		if(!memberVO.getPassword().equals(memberVO.getPwCheck())){
			check=true;
			//에러메세지 rejectValue("멤버변수명(path)", "properties의 key")
			bindingResult.rejectValue("pwCheck", "member.password.notEqual" );
		}
		
		//3. 아이디가 일치하는지 검증
		if(memberDAO.checkID(memberVO)>0) {
			//입력한 아이디가 0개 초과인경우
			check=true;
			bindingResult.rejectValue("id", "member.id.dupl");
		}
		
		return check;
	}
	
	public int checkID(MemberVO memberVO) throws Exception{
		return memberDAO.checkID(memberVO);
	}
	
	public int setJoin(MemberVO memberVO)throws Exception{
		int result = memberDAO.setJoin(memberVO);
		
		if(result==1) {
			MemberRoleVO memberRoleVO = new MemberRoleVO();
			memberRoleVO.setId(memberVO.getId());
			memberRoleVO.setNum(3); //회원가입하면 디폴트로 회원자격을 준다.
			result = memberDAO.setRole(memberRoleVO);
		}else {
			log.info("회원가입에 문제가 있음 | result :{} | memberVO : {}", result, memberVO);
			throw new Exception();
		}
		
		//롤 인서트 이후
		if(result<1) {
			log.info("롤 insert 테스트");
			throw new Exception();
		}
		
		return result;
	}
	
	//로그인 처리는 Security에서 처리함.
//	public MemberVO getLogin(MemberVO memberVO)throws Exception{
//		memberVO = memberDAO.getLogin(memberVO);
//		log.info("가져옴{}",memberVO);
//		return memberVO;
//	}
}
