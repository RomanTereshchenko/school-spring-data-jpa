package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;
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
		List<Course> coursesList = courseGeneratorService.generateCourses();
		assertNotNull(coursesList);
		assertEquals(10, coursesList.size());
	}

}
