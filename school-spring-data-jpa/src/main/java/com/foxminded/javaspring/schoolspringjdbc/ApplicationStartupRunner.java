package com.foxminded.javaspring.schoolspringjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;

@Component
@ConditionalOnProperty(prefix = "application", name = "env", havingValue = "prod")
public class ApplicationStartupRunner implements CommandLineRunner {
	
	private DBGeneratorService dbGeneratorService;

	@Autowired
	public ApplicationStartupRunner(DBGeneratorService dbGeneratorService) {
		this.dbGeneratorService = dbGeneratorService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		dbGeneratorService.startUp();
		dbGeneratorService.menu();
	}
	
	
	

}
