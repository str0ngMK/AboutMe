package com.about.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AboutMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AboutMeApplication.class, args);
	}

}
