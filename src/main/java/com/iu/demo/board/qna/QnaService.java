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
	@Value("${my.upload.file}")
	private String path;
	private String folder = "qna/";
	
	
	public int setFileDelete(QnaFileVO fileVO) throws Exception{
		//우선 파일정보를 가져옴
		fileVO = qnaMapper.getFileDetail(fileVO);
		//db정보 먼저 삭제
		int result = qnaMapper.setFileDelete(fileVO);
		//db삭제 완료 되면 파일 삭제
		if(result==1) {
			boolean result2 = fileManager.deleteFile(fileVO, path+folder);
			if(result2==false) {
				log.info("파일삭제 안됨");
			}
		}else {
			throw new Error("db삭제 안됨");
		}
		return result; 
	}
	
	public QnaFileVO getFileDetail(QnaFileVO fileVO) throws Exception{
		return qnaMapper.getFileDetail(fileVO);
	}
	
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
		
//		path = path+"qna/";//이렇게 하면 올릴때마다 계속 QNA폴더가 생성됨..
		log.info("realPath : {}",path+folder);
		
		File file = new File(path+folder);
		
		if(!file.exists()) {
			boolean chk = file.mkdirs();
			log.info("chk : {}",chk);
		}
		for(MultipartFile f:qnaVO.getFiles()) {
			if(!f.isEmpty()) {
				String fileName = fileManager.saveFile(f, path+folder); //0번째는 무조건 비어있음...?
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
