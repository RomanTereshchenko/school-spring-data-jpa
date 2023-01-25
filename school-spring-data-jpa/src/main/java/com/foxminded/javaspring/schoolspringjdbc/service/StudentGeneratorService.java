package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentGeneratorService {

	private Random random;
	private int nextUnassignedStudentID = 0;
	private List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");
	private List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
			"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
			"Gordon", "Mcgee", "Strong", "Philip");

	@Autowired
	public StudentGeneratorService(Random random) {
		this.random = random;
	}

	public List<Student> generateNStudents(int countToGenerate) {
		List<Student> studentsLocal = new ArrayList<>();
		IntStream.rangeClosed(1, countToGenerate)
				.forEach(studentID -> studentsLocal.add(new Student(getRandomFirstName(), getRandomLastName())));
		log.info("Students generated");
		return studentsLocal;
	}

	private String getRandomFirstName() {
		return studentFirstNames.get(random.nextInt(studentFirstNames.size()));
	}

	private String getRandomLastName() {
		return studentLastNames.get(random.nextInt(studentLastNames.size()));
	}

	@Transactional
	public void assignAllGroupsToAllItsStudents() {
		while (nextUnassignedStudentID < DBGeneratorService.students.size()) {
			IntStream.rangeClosed(1, DBGeneratorService.groups.size()).forEach(this::assignGroupToStudents);
		}
		log.info("Students assigned with groups");
	}

	private void assignGroupToStudents(int groupID) {
		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;
		while ((numberOfStudentsInGroup < limitOfStudentsInGroup)
				&& (nextUnassignedStudentID < DBGeneratorService.students.size())) {
			DBGeneratorService.students.get(nextUnassignedStudentID)
					.setGroup(new Group(groupID, DBGeneratorService.groups.get(groupID).getGroupName()));
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}
	}

	public void assignCoursesToAllStudents() {
		IntStream.rangeClosed(0, (DBGeneratorService.students.size() - 1)).forEach(this::assignCoursesToOneStudent);
		log.info("Students assigned with lists of courses");
	}

	private void assignCoursesToOneStudent(int nextStudentID) {
		final int maxNumberOfCourses = 3;
		int numberOfCoursesLimit = (random.nextInt(maxNumberOfCourses - 1) + 1);
		int numberOfAssignedCourses = 1;
		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 0; i < DBGeneratorService.courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;
		Set<Course> coursesOfStudentSet = new HashSet<>();
		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			coursesOfStudentSet.add(DBGeneratorService.courses.get(courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;
		}
		DBGeneratorService.students.get(nextStudentID).setCourses(coursesOfStudentSet);
	}

}
