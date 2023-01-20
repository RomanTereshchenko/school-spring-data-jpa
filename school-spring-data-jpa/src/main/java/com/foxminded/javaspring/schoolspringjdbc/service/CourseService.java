package com.foxminded.javaspring.schoolspringjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.JPACourseDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CourseService {
	
	private JPACourseDao jpaCourseDao;

	@Autowired
	public CourseService(JPACourseDao jpaCourseDao) {
		this.jpaCourseDao = jpaCourseDao;
	}
	
	public void addAllCoursesToDB() {
		DBGeneratorService.courses.forEach(course -> jpaCourseDao.saveCourse(course));
		log.info("Courses added to School database");
	}

}
