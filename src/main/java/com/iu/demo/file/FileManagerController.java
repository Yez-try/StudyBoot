package com.iu.demo.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.iu.demo.board.qna.QnaFileVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileManagerController {

	@GetMapping("/fileDown/{p}") // : url 주소의 일부분을 데이터로 사용할 수 있음 (나중 Restful, RestAPI와도 관련있음)
	public ModelAndView fileDown(QnaFileVO fileVO, @PathVariable(name="p") String path /*pathVariable은 경로에 있는 변수의 값을 path에 저장시켜서 활용한다.*/) throws Exception{
		log.info("fileNum {}", fileVO.getFileNum());
		
		ModelAndView mv = new ModelAndView();
		
		
		//DB에서 정보조회
		fileVO.setFileName("066fc296-b850-47ef-9af1-382a40e3b047_에스파.jpg");
		fileVO.setOriName("에스파.jpg");
		
		mv.addObject("path", path);
		mv.addObject("fileVO", fileVO);
		mv.setViewName("fileManager"); //뷰의 Bean의 이름으로 설정해주어야 한다.
		//그렇게 하면 jsp를 찾으러 가는 것이 아니라 Bean을 실행하러 간다.
		
		return mv;
	}
	
}
