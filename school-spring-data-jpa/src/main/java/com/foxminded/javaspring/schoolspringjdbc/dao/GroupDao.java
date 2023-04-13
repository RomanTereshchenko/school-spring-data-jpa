package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

	@Query(value = "SELECT g.* FROM school.groups g INNER JOIN school.students s "
			+ "ON g.group_id = s.group_id GROUP BY g.group_id HAVING COUNT (g.group_id)  <= :studentCount", nativeQuery = true)
	public List<Group> findByStudentsCount(@Param("studentCount") Integer studentsCount);

}
