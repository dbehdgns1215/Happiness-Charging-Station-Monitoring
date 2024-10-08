package com.example.happyDream;

import com.example.happyDream.Service.ChargerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HappyDreamApplication implements CommandLineRunner {

	@Autowired
	private ChargerService chargerService;
	public static void main(String[] args) {
		SpringApplication.run(HappyDreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// JSON 파일 경로 설정
		String filePath = "src/main/resources/data/전국전동휠체어급속충전기표준데이터.json";
		// JSON 데이터 저장 메서드 호출
		chargerService.saveChargersFromJson(filePath);
	}
}
