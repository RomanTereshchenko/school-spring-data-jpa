package com.foxminded.javaspring.schoolspringjdbc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.CourseDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CourseService {
	
	private CourseDao courseDao;
	
	public void addAllCoursesToDB() {
		DBGeneratorService.courses.forEach(course -> courseDao.save(course));
		log.info("Courses added to School database");
	}

}
