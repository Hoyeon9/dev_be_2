package com.restapi.emp;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackEndRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndRestApiApplication.class, args);
	}

	//Set Hibernate6Module object of Jackson Datatype Hibernate as Spring Bean
	//Force Lazy loading to get data
	@Bean
	Hibernate6Module hibernate6Module() {
		Hibernate6Module hibernate6Module = new Hibernate6Module();
		hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING,true);
		return hibernate6Module;
	}
}
