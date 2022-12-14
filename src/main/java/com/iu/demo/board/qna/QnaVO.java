package com.iu.demo.board.qna;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QnaVO { //VO는 Value Object DTO와 비슷한 역할
	
	private Long num;
	private String title;
	private String writer;
	private String contents;
	private Long hit;
	private Date regDate;
	private Long ref;
	private Long step;
	private Long depth;
	private MultipartFile[] files;
	private List<QnaFileVO> fileVOs;
	

}
