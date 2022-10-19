package com.iu.demo.board.qna;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iu.demo.util.Pager;

@SpringBootTest
class QnaMapperTest {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QnaMapper qnaMapper;
	
	private QnaVO qnaVO;
	
	private Pager pager;

//	@BeforeAll //일반 메서드로는 실행되지 않는다. static으로만 실행시켜야 한다. 공통으로 뭔가 세팅하고 싶은 경우 사용한다.
	static void beforeAll() {
		System.out.println("전체 테스트 실행전 한번 실행");

		
	}
//	
//	@AfterAll
//	static void afterAll() {
//		System.out.println("전체 테스트 실행 후 한번 실행");
//	}
//	
	@BeforeEach
	void beforEach() {
		log.info("비포이치 테스트 메서드 실행 전");
		pager = new Pager();
		pager.setSearch("2");
		pager.setPage(1);
		pager.setPerPage(10);
		
		pager.calRow();
	}
//	
//	@AfterEach
//	void afterEach() {
//		log.info("Test 메서드 실행 후");
//	}
//	
//	@Test
//	void test2() {
//		log.info("TEst2 case");
//	}
	
//	@Test
	void inserttest() throws Exception{
		
		for(int i = 0; i<100; i++) {
			
			QnaVO qna = new QnaVO();
			qna.setContents("콘텐츠"+i);
			qna.setTitle("타이틀"+i);
			qna.setWriter("라이터"+i);
			
			int result = qnaMapper.addQna(qna);
			
			log.info("result{}", result);
			assertEquals(1,result);
		}
	}
	
//	@Test
	void test() throws Exception{
	
		List<QnaVO> ar = qnaMapper.getList(pager);
		log.info("List{},{}",ar.size(), ar);
		assertNotNull(ar);
	}

}
