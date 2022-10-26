package com.iu.demo.member;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("join")
	public void setJoin() throws Exception{
		log.info("joinController");
	}
	
	@PostMapping("join")
	public String setJoin(MemberVO memberVO, RedirectAttributes attributes) throws Exception{
		log.info("membervo {}", memberVO);
		int result = memberService.setJoin(memberVO);
		log.info("join result: {}", result);
		
		attributes.addAttribute("result", result);
		return "redirect:/";
	}
	
	@GetMapping("login")
	public void getLogin() throws Exception{
		log.info("로그인 컨트롤러");
	}
	
	
	@PostMapping("login")
	public String getLogin(MemberVO memberVO, HttpSession session, HttpServletResponse response) throws Exception{
		log.info("MemberVO {}", memberVO);
		memberVO= memberService.getLogin(memberVO);
		
		//tcp 신뢰성 있는 연결, 응답이 무조건 오는 연결방식
		// session을 사용하면 요청이 발생하면 요청에 대해 request객체를 만들어 담는다.
		// 쿠키에 SessionID를 담아서 보내주는데...
		session.setAttribute("member", memberVO);
		
		Cookie cookie = new Cookie("login", memberVO.getId());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(MemberVO memberVO, HttpSession session) throws Exception{
		session.invalidate(); //세션을 만료시킨다.
		return "redirect:/";
	}
	
	
}
