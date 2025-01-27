package br.com.rest.excel.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExcelDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelDataApplication.class, args);
	}

}
