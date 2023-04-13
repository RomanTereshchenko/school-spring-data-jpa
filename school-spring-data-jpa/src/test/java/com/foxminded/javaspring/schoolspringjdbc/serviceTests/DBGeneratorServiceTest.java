package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.TablesDao;
import com.foxminded.javaspring.schoolspringjdbc.service.CourseGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.CourseService;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupService;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentService;

@ExtendWith (MockitoExtension.class)
class DBGeneratorServiceTest {
	
	@Mock
	private TablesDao jpaTablesDao;
	@Mock
	private GroupGeneratorService groupGeneratorService;
	@Mock
	private GroupService groupService;
	@Mock
	private CourseGeneratorService courseGeneratorService;
	@Mock
	private CourseService courseService;	@Mock	private StudentGeneratorService studentGeneratorService;
	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private DBGeneratorService dbGeneratorService;
	
	@Test
	void testStartUp() {
		
		dbGeneratorService.startUp();
		verify(jpaTablesDao).truncateTables();
		verify(groupGeneratorService).generateNGroups(anyInt());
		verify(groupService).addAllGroupsToDB();
		verify(courseGeneratorService).generateCourses();
		verify(courseService).addAllCoursesToDB();
		verify(studentGeneratorService).generateNStudents(anyInt());
		verify(studentService).addStudentsToDB();
		verify(studentGeneratorService).assignAllGroupsToAllItsStudents();
		verify(studentService, times(2)).updateAllStudentsInDB();
		verify(studentGeneratorService).assignCoursesToAllStudents();
	}
	
}
