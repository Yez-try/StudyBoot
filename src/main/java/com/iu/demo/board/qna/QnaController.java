package com.iu.demo.board.qna;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
