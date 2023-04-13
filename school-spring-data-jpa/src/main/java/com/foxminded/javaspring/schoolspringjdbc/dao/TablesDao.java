package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TablesDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public TablesDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void truncateTables() {
		jdbcTemplate.execute("TRUNCATE TABLE school.groups CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.groups_group_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.groups ALTER COLUMN group_id SET DEFAULT nextval('school.groups_group_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.courses CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.courses_course_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.courses ALTER COLUMN course_id SET DEFAULT nextval('school.courses_course_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.students_student_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.students ALTER COLUMN student_id SET DEFAULT nextval('school.students_student_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students_courses CASCADE;");
	}
}
