package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.StudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
@Transactional
@SpringBootTest
class StudentServiceTest {
	
	@Mock
	@PersistenceContext
	private EntityManager em;

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private StudentService studentService;

	@Test
	void testFindStudentsRelatedToCourse() {
		List<Student> studentsOfCourse = new ArrayList<>();
		studentsOfCourse.add(new Student("TestFirstName", "TestLastName"));
		Mockito.when(scannerUtil.scanString()).thenReturn("TestCourse");
		Mockito.when(studentDao.findStudentsRelatedToCourse("TestCourse")).thenReturn(studentsOfCourse);
		List<Student> result = studentService.findStudentsRelatedToCourse();
		assertEquals("TestFirstName", result.get(0).getFirstName());
		assertEquals("TestLastName", result.get(0).getLastName());
	}

	@Test
	void testAddNewStudent() {
		Mockito.when(scannerUtil.scanString()).thenReturn("TestName");
		studentService.addNewStudent();
		verify(studentDao).save(any(Student.class));
	}

	@Test
	void testDeleteStudent() {
		Mockito.when(scannerUtil.scanInt()).thenReturn(1);
		studentService.deleteStudent();
		verify(studentDao).deleteById(anyInt());
	}
	
	@Test
	void testAddStudentToCourse() {
		int courseAndStudentId = 1;
		Mockito.when(scannerUtil.scanInt()).thenReturn(courseAndStudentId);
		DBGeneratorService.students.add(new Student(courseAndStudentId, "TestFName", "TestLName"));
		DBGeneratorService.courses.add(new Course(courseAndStudentId, "TestCourse"));
		studentService.addStudentToCourse();
		verify(scannerUtil, times(2)).scanInt();
		verify(studentDao).save(DBGeneratorService.students.get(0));
		DBGeneratorService.students.clear();
		DBGeneratorService.courses.clear();
	}
	
	@Test
	@Transactional
	void testRemoveStudentFromCourse() {
		int courseAndStudentId = 1;
		DBGeneratorService.students.add(new Student(courseAndStudentId, "TestFName", "TestLName"));
		DBGeneratorService.courses.add(new Course(courseAndStudentId, "TestCourse"));
		Student student = DBGeneratorService.students.get(0);
		student.getCourses().add(DBGeneratorService.courses.get(0));
		em.persist(student);
		Mockito.when(scannerUtil.scanInt()).thenReturn(courseAndStudentId);
		studentService.removeStudentFromCourse();
		verify(scannerUtil, times(2)).scanInt();
		verify(studentDao).save(student);
		DBGeneratorService.students.clear();
		DBGeneratorService.courses.clear();
	}
	
}
