package com.iu.demo.board.qna;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.demo.util.FileManager;
import com.iu.demo.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QnaService {

	@Autowired
	private QnaMapper qnaMapper;
	@Autowired
	private FileManager fileManager;
	@Value("${my.upload.qna}")
	private String path;
	
	public QnaVO getDetail(QnaVO qnaVO) throws Exception{
		return qnaMapper.getDetail(qnaVO);
	}
	
	public List<QnaVO> getList(Pager pager) throws Exception{
		pager.calRow();
		List<QnaVO> lst = qnaMapper.getList(pager);
		
		return lst;
	}
	
	public int write(QnaVO qnaVO, HttpSession session) throws Exception{
		
		int result = qnaMapper.addQna(qnaVO);
//		String realPath = session.getServletContext().getRealPath("/static/upload/qna2");
		
		log.info("realPath : {}",path);
		
		File file = new File(path);
		
		if(!file.exists()) {
			boolean chk = file.mkdirs();
			log.info("chk : {}",chk);
		}
		for(MultipartFile f:qnaVO.getFiles()) {
			if(!f.isEmpty()) {
				String fileName = fileManager.saveFile(f, path); //0번째는 무조건 비어있음...?
				log.info("filName: {}",fileName);
				QnaFileVO fileVO = new QnaFileVO();
				fileVO.setNum(qnaVO.getNum());
				fileVO.setFileName(fileName);
				fileVO.setOriName(f.getOriginalFilename());
				
				qnaMapper.setFileAdd(fileVO);
			}
		}
		
	
		return result;
	}
}
