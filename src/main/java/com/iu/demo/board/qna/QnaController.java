package com.iu.demo.board.qna;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iu.demo.util.Pager;

@Controller
@RequestMapping("qna")
public class QnaController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QnaService qnaService;
	
	@GetMapping("list")
	public ModelAndView getList(Pager pager, ModelAndView mv) throws Exception{
		List<QnaVO> lst = qnaService.getList(pager);
		
		mv.addObject("lst", lst);
		mv.setViewName("board/list");
		
		return mv;
	}
	
	@GetMapping("write")
	public String write() throws Exception{
		return "board/write";
	}
	
	@PostMapping("write")
	public String write(QnaVO qna, RedirectAttributes redirectAttributes) throws Exception{
		int result = qnaService.write(qna);
		log.info("result{}", result);
		
		//redirectAttribute는 인터페이스 형식이다.
		//아래 mv 방식으로는 result를 꺼내쓸 수 없다.
//		mv.addObject("result", result);
//		mv.setViewName("redirect:./list");
		
		redirectAttributes.addAttribute("result", result);
		return "redirect:./write";
	}
}
