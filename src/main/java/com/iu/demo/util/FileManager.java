package com.iu.demo.util;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileManager {

	public String saveFile(MultipartFile multipartFile, String path) throws Exception {

		
		String fileName = UUID.randomUUID().toString();
		
		StringBuffer bf = new StringBuffer();
		
		bf.append(fileName);
		bf.append("_");
		bf.append(multipartFile.getOriginalFilename());
		
		//한글 파일이 문제인경우, origanalName의 한글은 빼고 확장자만 가져와보자
		String ex = multipartFile.getOriginalFilename();
		ex = ex.substring(ex.lastIndexOf(".")); // 뒤에서부터 점의 위치를 찾고 거기부터 끝까지 가져와라
		log.info("확장자 : {}",ex);
		
		log.info("fileName {}", bf.toString());
		
		//filesave
		File file = new File(path, bf.toString());
		
//		FileCopyUtils.copy(multipartFile.getBytes(), file);
		multipartFile.transferTo(file);//
		
		
		return bf.toString();
	}
	
	//파일 삭제
	
	
}
