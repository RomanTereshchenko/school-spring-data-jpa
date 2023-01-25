package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.model.Student;
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
		List<Student> studentList = studentGeneratorService.generateNStudents(10);
        assertNotNull(studentList);
        assertEquals(10, studentList.size());
	}

}
