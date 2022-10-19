package com.iu.demo.util;

import lombok.Data;

@Data
public class Pager {

	private String kind;
	private String search;
	private Integer page;
	private Integer perPage;
	private Integer startRow;
	
	public void calRow() {
		this.startRow = (this.getPage()-1)*this.perPage;
	}
	
}
