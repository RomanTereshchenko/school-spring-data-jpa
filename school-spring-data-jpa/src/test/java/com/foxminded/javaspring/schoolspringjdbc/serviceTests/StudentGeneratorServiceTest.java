package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.service.StudentGeneratorService;

@SpringBootTest
class StudentGeneratorServiceTest {
	
	private StudentGeneratorService studentGeneratorService;

	@Autowired
	public StudentGeneratorServiceTest(StudentGeneratorService studentGeneratorService) {
		this.studentGeneratorService = studentGeneratorService;
	}
	
	@Test
	void testGenerateNStudents() {
		assertNotNull(studentGeneratorService.generateNStudents(10));
		assertEquals(10, studentGeneratorService.generateNStudents(10).size());	
	}

}
