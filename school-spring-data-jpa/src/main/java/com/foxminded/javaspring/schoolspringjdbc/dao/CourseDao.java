package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

//	23.01.2023 18:56
}
