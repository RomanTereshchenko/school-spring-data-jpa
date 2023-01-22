package com.foxminded.javaspring.schoolspringjdbc.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.StudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@SpringBootTest
class StudentDaoTest {

	private StudentDao studentDao;
	@PersistenceContext
	private EntityManager em;

	@Autowired
	public StudentDaoTest(StudentDao studentDao, EntityManager em) {
		this.studentDao = studentDao;
		this.em = em;
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
	void testSaveStudent() {
		studentDao.save(new Student("TestFName", "TestLName"));
		Student student = (Student) em
				.createNativeQuery("SELECT * FROM school.students WHERE first_name = ? AND last_name = ?;",
						Student.class)
				.setParameter(1, "TestFName").setParameter(2, "TestLName").getSingleResult();
		assertNotNull(student);
		assertEquals("TestFName", student.getFirstName());
		assertEquals("TestLName", student.getLastName());
	}

	@Test
	@Transactional
	void testDeleteStudentFromDB() {
		Student delStudent = new Student();
		delStudent.setFirstName("DelStudentFName");
		delStudent.setLastName("DelStudentLName");
		em.persist(delStudent);
		Student student = (Student) em
				.createNativeQuery("SELECT * from school.students WHERE "
						+ "first_name = 'DelStudentFName' AND last_name = 'DelStudentLName'", Student.class)
				.getSingleResult();
		assertNotNull(student);
		studentDao.deleteById(student.getStudentID());
		assertThrows(NoResultException.class, () -> {
			em.createNativeQuery("Select * from school.students WHERE first_name = ? AND " + "last_name = ?;",
					Student.class).setParameter(1, delStudent.getFirstName()).setParameter(2, delStudent.getLastName())
					.getSingleResult();

		});
	}

	@Test
	@Transactional
	void testUpdateStudent() {
		Student updatingStudent = new Student();
		updatingStudent.setFirstName("StudentFName");
		updatingStudent.setLastName("StudentLName");
		em.persist(updatingStudent);
		Group group = new Group();
		group.setGroupName("tt-11");
		em.persist(group);
		updatingStudent.setGroupID(1);
		studentDao.save(updatingStudent);
		Student student = (Student) em
				.createNativeQuery("SELECT * from school.students WHERE "
						+ "first_name = 'StudentFName' AND last_name = 'StudentLName'", Student.class)
				.getSingleResult();
		assertEquals(1, student.getGroupID());
	}

	@Test
	@Transactional
	void findStudentsRelatedToCourse_ReturnsCorrectListOfStudentsAssignedToSelectedCourse() {
		Student student = new Student();
		student.setFirstName("StudentFName");
		student.setLastName("StudentLName");
		em.persist(student);
		Course course = new Course();
		course.setCourseName("TestCourse");
		em.persist(course);
		student.addCourse(course);
		em.merge(student);
		List<Student> studentsRelatedToCourse = studentDao.findStudentsRelatedToCourse("TestCourse");
		assertEquals(1, studentsRelatedToCourse.size());
		assertEquals("StudentFName", studentsRelatedToCourse.get(0).getFirstName());
		assertEquals("StudentLName", studentsRelatedToCourse.get(0).getLastName());
	}

}
