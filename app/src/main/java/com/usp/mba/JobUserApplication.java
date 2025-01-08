package com.usp.mba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication(scanBasePackages = {"com.usp.mba.adapters.persistence", "com.usp.mba.adapters.controller", "com.usp.mba.adapters.config"})
@EnableJpaRepositories(basePackages = {"com.usp.mba.adapters.persistence"})
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class JobUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobUserApplication.class, args);
	}

}
