package com.foxminded.javaspring.schoolspringjdbc.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.CourseDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;

@SpringBootTest
class CourseDaoTest {

	private CourseDao courseDao;
	@PersistenceContext
	private EntityManager em;

	
	@Autowired
	public CourseDaoTest(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@BeforeEach
	void truncateTables() {
		em.createNativeQuery("TRUNCATE TABLE school.groups CASCADE;").executeUpdate();
		em.createNativeQuery("ALTER SEQUENCE school.groups_group_id_seq RESTART WITH 1;").executeUpdate();
		em.createNativeQuery(
				"ALTER TABLE school.groups ALTER COLUMN group_id SET DEFAULT nextval('school.groups_group_id_seq');")
				.executeUpdate();
		em.createNativeQuery("TRUNCATE TABLE school.courses CASCADE;").executeUpdate();
		em.createNativeQuery("ALTER SEQUENCE school.courses_course_id_seq RESTART WITH 1;").executeUpdate();
		em.createNativeQuery(
				"ALTER TABLE school.courses ALTER COLUMN course_id SET DEFAULT nextval('school.courses_course_id_seq');")
				.executeUpdate();
		em.createNativeQuery("TRUNCATE TABLE school.students CASCADE;").executeUpdate();
		em.createNativeQuery("ALTER SEQUENCE school.students_student_id_seq RESTART WITH 1;").executeUpdate();
		em.createNativeQuery(
				"ALTER TABLE school.students ALTER COLUMN student_id SET DEFAULT nextval('school.students_student_id_seq');")
				.executeUpdate();
		em.createNativeQuery("TRUNCATE TABLE school.students_courses CASCADE;").executeUpdate();
	}

	@Test
	@Transactional
	void testSaveCourse() {
		courseDao.save(new Course("TestCourse"));
		Course course = (Course) em.createNativeQuery("SELECT * FROM school.courses c WHERE c.course_name = ?",
				Course.class).setParameter(1, "TestCourse").getSingleResult();
		assertNotNull(course);
		assertEquals("TestCourse", course.getCourseName());
	}

}
