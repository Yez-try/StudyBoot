package com.iu.demo.board.qna;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@PostMapping("summerFileDelete")
	@ResponseBody
	public String setSummerFileDelete(String fileName)throws Exception{
		fileName = fileName.substring(fileName.lastIndexOf("/")+1);
		log.info("fileName {} ",fileName);
		return "1";
	}

	@PostMapping("summerFile")
	@ResponseBody
	public String summernote(MultipartFile files) throws Exception{
		log.info("files {}",files);
		//원래는 Service에 작업
		String result = qnaService.setSummerFile(files);
		
		return result;
	}
	
	@GetMapping("hack")
	@ResponseBody
	public int hack(QnaVO qnaVO) throws Exception{
		qnaService.write(qnaVO);
		return 1;
	}
	
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
	public String write(QnaVO qna, RedirectAttributes redirectAttributes, HttpSession session) throws Exception{
		int result = qnaService.write(qna);
		log.info("result : {}", result);
		

		//redirectAttribute는 인터페이스 형식이다.
		//아래 mv 방식으로는 result를 꺼내쓸 수 없다.
//		mv.addObject("result", result);
//		mv.setViewName("redirect:./list");
		
		redirectAttributes.addAttribute("result", result);
		return "redirect:./detail?num="+qna.getNum();
	}
	
	@GetMapping("detail")
	public ModelAndView getDetail(QnaVO qnaVO, ModelAndView mv)throws Exception{
		qnaVO = qnaService.getDetail(qnaVO);
		
		mv.addObject("qnaVO", qnaVO);
		mv.setViewName("board/detail");
		
		return mv;
	}
	
	@GetMapping("update")
	public ModelAndView getUpdate(QnaVO qnaVO, ModelAndView mv)throws Exception{
		qnaVO = qnaService.getDetail(qnaVO);
		
		mv.addObject("qnaVO", qnaVO);
		mv.setViewName("board/update");
		
		return mv;
	}
	
	
	@PostMapping("filedelete")
	@ResponseBody
	public int setFileDelete(QnaFileVO fileVO)throws Exception{
		int result =qnaService.setFileDelete(fileVO);
		log.info("----------filevo----------{}", fileVO);
		
		return result;
	}
}
