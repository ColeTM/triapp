package com.example.triapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class TriappApplication {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	  return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
		  registry.addMapping("/api/**")
				  .allowedOrigins("http://localhost:5173")
				  .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
		}
	  };
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TriappApplication.class, args);
	}

}
