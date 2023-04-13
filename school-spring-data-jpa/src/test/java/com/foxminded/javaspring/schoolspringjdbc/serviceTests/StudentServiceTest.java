package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
		Mockito.when(studentDao.findStudentsRelatedToCourse(anyString())).thenReturn(studentsOfCourse);		
		List<Student> result = studentService.findStudentsRelatedToCourse();
		assertEquals("TestFirstName", result.get(0).getFirstName());
		assertEquals("TestLastName", result.get(0).getLastName());
		verify(scannerUtil).scanString();
		verify(studentDao).findStudentsRelatedToCourse(anyString());
	}
	
	@Captor
	ArgumentCaptor<Student> studentCaptor;

	@Test
	void testAddNewStudent() {
		Mockito.when(scannerUtil.scanString()).thenReturn("TestName");
		studentService.addNewStudent();
		verify(studentDao).save(studentCaptor.capture());
		assertEquals("TestName", studentCaptor.getValue().getFirstName());
		assertEquals("TestName", studentCaptor.getValue().getLastName());
		assertNull(studentCaptor.getValue().getStudentID());
		assertNull(studentCaptor.getValue().getGroup());
	}

	@Test
	void testDeleteStudent() {
		int studentIdToDelete = 1;
		Mockito.when(scannerUtil.scanInt()).thenReturn(studentIdToDelete);
		studentService.deleteStudent();
		verify(studentDao).deleteById(eq(studentIdToDelete));
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
		verify(studentDao).save(any(Student.class));
		verify(studentDao).save(studentCaptor.capture()); 
		assertEquals("TestFName", studentCaptor.getValue().getFirstName());
		assertEquals("TestLName", studentCaptor.getValue().getLastName());
		assertEquals(1, studentCaptor.getValue().getStudentID());
		assertNull(studentCaptor.getValue().getGroup());

		DBGeneratorService.students.clear();
		DBGeneratorService.courses.clear();
	}
	
}
