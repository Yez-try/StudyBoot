package com.iu.demo.board.qna;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.iu.demo.util.Pager;

//@Repository 생략가능
@Mapper
public interface QnaMapper {
	
	public List<QnaVO> getList(Pager pager) throws Exception;
	public int addQna(QnaVO qnaVO) throws Exception;
	public int setFileAdd(QnaFileVO fileVO) throws Exception;
	public QnaVO getDetail(QnaVO qnaVO) throws Exception;
	public QnaFileVO getFileDetail(QnaFileVO fileVO) throws Exception;
	public int setFileDelete(QnaFileVO fileVO) throws Exception;
}
