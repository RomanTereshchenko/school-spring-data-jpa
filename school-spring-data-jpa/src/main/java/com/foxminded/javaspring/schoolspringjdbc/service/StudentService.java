package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.StudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StudentService {

	private ScannerUtil scannerUtil;
	private StudentDao studentDao;

	@Autowired
	public StudentService(ScannerUtil scannerUtil) {
		this.scannerUtil = scannerUtil;
	}

	public void addStudentsToDB() {
		DBGeneratorService.students.forEach(student -> studentDao.save(student));
		log.info("Students added to School database");
	}

	public void updateAllStudentsInDB() {
		for (Student student : DBGeneratorService.students) {
			if (student.getGroupID() != 0) {
				studentDao.save(student);
			}
		}
		log.info("Students updated");
	}

	public List<Student> findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println("Enter a course name from the list: ");
		for (Course course : DBGeneratorService.courses) {
			System.out.println(course.getCourseName());
		}
		String courseName = scannerUtil.scanString();
		List<Student> studentsOfCourse = studentDao.findStudentsRelatedToCourse(courseName);
		for (Student student : studentsOfCourse) {
			System.out.println(student.getFirstName() + " " + student.getLastName());
		}
		System.out.println();
		return studentsOfCourse;
	}

	public void addNewStudent() {
		System.out.println("Add a new student");
		System.out.println("Enter the student first name");
		String firstName = scannerUtil.scanString();
		System.out.println("Enter the student last name");
		String lastName = scannerUtil.scanString();
		studentDao.save(new Student(firstName, lastName));
		System.out.println("New student " + firstName + " " + lastName + " is added to School database");
		System.out.println();
	}

	public void deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scannerUtil.scanInt();
		studentDao.deleteById(studentIdToDelete);
		System.out.println("Student with ID " + studentIdToDelete + " is deleted from School database");
		System.out.println();
	}

	public void addStudentToCourse() {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID");
		int studentId = scannerUtil.scanInt();
		System.out.println("The available courses are:");
		for (Course course : DBGeneratorService.courses) {
			System.out.println(course.getCourseID() + " - " + course.getCourseName());
		}
		System.out.println("Enter the course ID");
		int courseId = scannerUtil.scanInt();
		Student student = DBGeneratorService.students.get(studentId - 1);
		Set<Course> studentCourses = student.getCourses();
		for (Course studentCourse : studentCourses) {
			if (studentCourse.getCourseID() == courseId) {
				System.out.println("This student is already assigned to this course. Choose other student and course.");
				return;
			}
		}
		student.getCourses().add(DBGeneratorService.courses.get(courseId - 1));
		studentDao.save(student);
		System.out.println(
				"Course with ID " + courseId + " is assigned to student with ID " + studentId + " in School database");
	}

	public void removeStudentFromCourse() {
		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID");
		int studentIdToRemove = scannerUtil.scanInt();
		Student student = DBGeneratorService.students.get(studentIdToRemove - 1);
		Set<Course> studentCourses = student.getCourses();
		System.out.println("This student is assigned to the following courses:");
		for (Course studentCourse : studentCourses) {
			System.out.println(studentCourse.getCourseID() + " - "
					+ DBGeneratorService.courses.get(studentCourse.getCourseID() - 1).getCourseName());
		}
		System.out.println("Enter the course ID, from which to remove this student");
		int courseIdToRemove = scannerUtil.scanInt();
		for (Course studentCourse : studentCourses) {
			if (studentCourse.getCourseID() == courseIdToRemove) {
				student.getCourses()
						.remove(DBGeneratorService.courses.get(courseIdToRemove - 1));
				studentDao.save(student);
				System.out.println("Student with ID " + studentIdToRemove + " is removed from the course "
						+ courseIdToRemove + " in School database");
				return;
			}
		}
		System.out.println("This course is not assigned to this student. Choose other student and course");
	}
}
