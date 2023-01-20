package com.foxminded.javaspring.schoolspringjdbc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;

@Repository
public class JPACourseDao {

//	2.6
	@PersistenceContext
	private EntityManager em;

	public void saveCourse(Course course) {
		em.persist(course);
	}

}
