package com.iu.demo.board.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.demo.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QnaService {

	@Autowired
	private QnaMapper qnaMapper;
	
	public List<QnaVO> getList(Pager pager) throws Exception{
		pager.calRow();
		List<QnaVO> lst = qnaMapper.getList(pager);
		
		return lst;
	}
	
	public int write(QnaVO qnaVO) throws Exception{
		
		for(MultipartFile f:qnaVO.getFiles()) {
			if(!f.isEmpty()) {
				log.info("FileName : {}", f.getOriginalFilename());
			}
		}
		
		int result = qnaMapper.addQna(qnaVO);
		return 1;
	}
}
