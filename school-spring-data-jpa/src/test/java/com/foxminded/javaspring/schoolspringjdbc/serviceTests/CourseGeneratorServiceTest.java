package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.service.CourseGeneratorService;

@SpringBootTest
class CourseGeneratorServiceTest {
	
	private CourseGeneratorService courseGeneratorService;

	@Autowired
	public CourseGeneratorServiceTest(CourseGeneratorService courseGeneratorService) {
		this.courseGeneratorService = courseGeneratorService;
	}

	@Test
	void testGenerateCourses() {
		assertNotNull(courseGeneratorService.generateCourses());
		assertEquals(10, courseGeneratorService.generateCourses().size());
	}

}
