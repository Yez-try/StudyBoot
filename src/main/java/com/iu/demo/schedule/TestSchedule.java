package com.iu.demo.schedule;

import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestSchedule {
	
	@Scheduled(cron="0 0 9-18 * * 1-5")
	public void cron() {
//		log.info("Cron 매초 실행  : cron=\"* * * * * *\"");
//		log.info("매시 매분 10초에 실행: @Scheduled(cron=\"10 * * * * *\")");
//		log.info("매월 1일 자정마다 실행 : (cron=\"0 0 0 1 * *\")");
//		log.info("매일 3시간 간격으로 실행 : (cron=\"20 30 */3 * * *\") 0시 35분 20초");
//		log.info("매주 토-일 자정에 실행 : (cron=\"0 0 0 * * 6-7\")");
		log.info("매주 월-금요일까지 오전 9-18시까지 매시 정각마다 실행 @Scheduled(cron=\"0 0 9-18 * * 1-5\")");
		log.info("위와 동일한데 , 4시간 간격으로 실행 @Scheduled(cron=\"0 0 9-18/4 * * 1-5\")");
		log.info("월수금 자정마다 실행 @Scheduled(cron=\"0 0 0 * * 1,3,5\")");
		
	}

//	@Scheduled(/*fixedRate = 3000 일정한 간격으로 반복해서 실행, 해당 메서드가 언제 종료되는지는 상관이 없음. 밀리세컨드 단위*/ 
//			fixedDelay = 5000 /*메서드가 종료되고 난 후 일정간격 뒤에 실행*/,
//			initialDelayString = "1000" /*서버가 실행하고 처음 시작 간격을 설정한다*/)
	public void ts1() {
		log.info("Schedule 실행");
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
