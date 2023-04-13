package com.foxminded.javaspring.schoolspringjdbc.configuration;

import java.util.Random;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

	@Bean
	public Random random() {
		return new Random();
	}

	@Bean
	public Scanner scan() {
		return new Scanner(System.in);
	}

}
