package com.iu.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
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
