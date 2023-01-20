package com.foxminded.javaspring.schoolspringjdbc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JPATablesDao {

	@PersistenceContext
	private final EntityManager em;

	public JPATablesDao (EntityManager em) {
		this.em = em;
	}

	@Transactional
	public void truncateTables() {
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
}
