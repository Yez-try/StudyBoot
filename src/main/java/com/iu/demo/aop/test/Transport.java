package com.iu.demo.aop.test;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Transport {

	public void takeBus() {
		log.info("----------버스타기-------------");
		
	}
	
	public void takeSubway() {
		log.info("----------지하철타기------------");
	}
	
	public void getTaxi() {
		log.info("---------택시타기------------");
	}
	
	public void airplane() {
		log.info("----------비행기붕붕-----------");
	}
}
