package com.iu.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
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
	
	public MemberVO getLogin(MemberVO memberVO)throws Exception{
		memberVO = memberDAO.getLogin(memberVO);
		log.info("가져옴{}",memberVO);
		return memberVO;
	}
}
