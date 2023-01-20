package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@Repository

public class JPAStudentDao {

	@PersistenceContext
	private EntityManager em;

	public void saveStudent(Student student) {
		em.persist(student);
	}

	public int deleteStudentFromDB(int studentID) {
		return em.createQuery("DELETE FROM Student s WHERE s.studentID = :studentID")
				.setParameter("studentID", studentID).executeUpdate();
	}

	@Modifying
	public void updateStudent(Student student) {
		em.merge(student);
	}

	public List<Student> findStudentsRelatedToCourse(String courseName) {
		return em
				.createQuery("SELECT s FROM Student s "
						+ "INNER JOIN s.courses c WHERE c.courseName = :courseName", Student.class)
				.setParameter("courseName", courseName).getResultList();
	}

}
