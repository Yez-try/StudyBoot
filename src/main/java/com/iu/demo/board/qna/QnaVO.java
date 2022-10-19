package com.iu.demo.board.qna;

import java.sql.Date;

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
	

}
