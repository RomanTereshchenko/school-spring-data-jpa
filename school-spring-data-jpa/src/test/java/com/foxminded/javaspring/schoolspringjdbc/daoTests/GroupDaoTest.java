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

import com.foxminded.javaspring.schoolspringjdbc.dao.GroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@SpringBootTest
class GroupDaoTest {

	private GroupDao groupDao;
	@PersistenceContext
	private final EntityManager em;

	@Autowired
	public GroupDaoTest(GroupDao groupDao, EntityManager em) {
		this.groupDao = groupDao;
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
	void testSaveGroup() {
		groupDao.save(new Group("tt-00"));
		Group group = (Group) em.createNativeQuery(
				"SELECT * FROM school.groups g WHERE group_name = ?",
				Group.class).setParameter(1, "tt-00").getSingleResult();
		assertNotNull(group);
		assertEquals("tt-00", group.getGroupName());
	}

	@Test
	@Transactional
	void selectGroupsByStudentsCount_ReturnsGroupWithSelectedStudentCount() {
		em.persist(new Group("tt-00"));
		Student student1 = new Student();
		student1.setGroupID(1);
		student1.setFirstName("TestFName1");
		student1.setLastName("TestLName1");
		em.persist(student1);
		Student student2 = new Student();
		student2.setGroupID(1);
		student2.setFirstName("TestFName2");
		student2.setLastName("TestLName2");
		em.persist(student2);
		assertEquals("tt-00", groupDao.findByStudentsCount(2).get(0).getGroupName());
	}

}
