package br.com.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AvaliacaoMetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoMetaApplication.class, args);
	}

}
