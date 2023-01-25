package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseGeneratorService {

	public final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
			"Music", "Leadership", "Speech", "English", "Algebra");

	public List<Course> generateCourses() {
		List<Course> coursesLocal = new ArrayList<>();
		for (int i = 1; i <= courseNames.size(); i++) {
			coursesLocal.add(new Course(courseNames.get(i - 1)));
		}
		log.info("Courses generated");
		return coursesLocal;
	}
	
	

}
