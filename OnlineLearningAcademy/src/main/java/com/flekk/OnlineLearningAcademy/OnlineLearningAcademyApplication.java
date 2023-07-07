package com.flekk.OnlineLearningAcademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class OnlineLearningAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLearningAcademyApplication.class, args);

	}


}
