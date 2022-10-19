package com.iu.demo.board.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iu.demo.util.Pager;

@Service
public class QnaService {

	@Autowired
	private QnaMapper qnaMapper;
	
	public List<QnaVO> getList(Pager pager) throws Exception{
		pager.calRow();
		List<QnaVO> lst = qnaMapper.getList(pager);
		
		return lst;
	}
}
