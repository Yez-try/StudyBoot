package com.iu.demo.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;

import com.iu.demo.board.qna.QnaFileVO;

import lombok.extern.slf4j.Slf4j;

@Component("fileManager") // 이름을 지정해주지 않으면 클래스의 첫글자를 소문자로 변경해 빈의 이름으로 생성한다.
@Slf4j
//abstarctView는 스프링이 인정하는 view 클래스이다. (이렇게 만든 View 클래스를 Custom View 라고 한다.)
public class FileManager extends AbstractView {

	@Value("${my.upload.file}")
	private String base;
	
	@Override // 이게 다운로드를 걸어주는 메서드
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("======================================");
		log.info("File DownLoad view");
		
		
		//컨트롤러에서 mv.addObject("fileVO", fileVO);로 보내준 qnafileVO가 model 에 담겨 전달된다.
		QnaFileVO fileVO = (QnaFileVO)model.get("fileVO");
		String path = (String)model.get("path");
		log.info("---------------------------------------");
		log.info("qnaFileVO:{}",fileVO);
		
		File file = new File(base+path+"/", fileVO.getFileName());
		
		//한글 처리 
		response.setCharacterEncoding("UTF-8");
		
		//총 파일의 크기
		response.setContentLengthLong(file.length());//file.length()는 파일의 크기를 뜻한다.
		
		//다운로드시 파일의 이름을 인코딩
		String oriName = URLEncoder.encode(fileVO.getOriName(), "UTF-8");
		
		//header 설정 (부가적인 정보 의미, 이파일이 뭐고, 어떤 형식이다를 뜻함)
		response.setHeader("Content-Disposition", "attachment;filename=\""+oriName+"\"");
		response.setHeader("Content-Transfer-Encoding", "binary");//전송할 때의 파일 형식을 의미 (text가 아닌 이진 파일이라고 알려줌)
		
		// HDD에서 파일을 읽고(FileInputStram으로 읽어옴)
		FileInputStream fi = new FileInputStream(file);
		// Client로 전송준비
		OutputStream os = response.getOutputStream(); //클라이언트로 연결된 스트림을 가져온다.
		
		//실제 전송하기
		FileCopyUtils.copy(fi, os);
		
		//자원 해제
		os.close();
		fi.close();
		
	}
	
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
	public boolean deleteFile(QnaFileVO fileVO, String path)throws Exception{
		log.info("파일매니저 삭제 실행{}{}",path, fileVO.getFileName());
		
		File file = new File(path, fileVO.getFileName());
		
		boolean result = file.delete();
		
		
		return result;
	}
	
	
}
