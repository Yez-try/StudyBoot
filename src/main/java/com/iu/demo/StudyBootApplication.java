package com.iu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableAspectJAutoProxy
@EnableScheduling
public class StudyBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyBootApplication.class, args);
	}

}
