package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

	@Query(value = "SELECT s.student_id, s.first_name, s.last_name "
			+ "FROM school.students s INNER JOIN school.students_courses sc ON s.student_id = sc.student_id "
			+ "INNER JOIN school.courses c ON c.course_id = sc.course_id WHERE course_name = :courseName", nativeQuery = true)
	public List<Student> findStudentsRelatedToCourse (@Param("courseName") String courseName);
}
