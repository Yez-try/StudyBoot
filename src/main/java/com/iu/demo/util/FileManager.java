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
		
		log.info("fileName {}", bf.toString());
		
		//filesave
		File file = new File(path, bf.toString());
		
//		FileCopyUtils.copy(multipartFile.getBytes(), file);
		multipartFile.transferTo(file);//
		
		
		return bf.toString();
	}
}
