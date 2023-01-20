package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.JPAGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GroupService {

	private ScannerUtil scannerUtil;
	private JPAGroupDao jpaGroupDao;

	@Autowired
	public GroupService(ScannerUtil scannerUtil, JPAGroupDao jpaGroupDao) {
		this.scannerUtil = scannerUtil;
		this.jpaGroupDao = jpaGroupDao;
	}


	public void addAllGroupsToDB() {
		DBGeneratorService.groups.forEach(group -> jpaGroupDao.saveGroup(group));
		log.info("Groups added to School database");
	}
	
	public List<Group> findGroupsByStudentsCount() {
		System.out.println("Find all groups with less or equal students� number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scannerUtil.scanInt();
		List<Group> selectedGroups = jpaGroupDao.selectGroupsByStudentsCount(lessOrEqualNum);
		for (Group group : selectedGroups) {
			System.out.println(group.getGroupName());
		}
		System.out.println();
		return selectedGroups;
	}

}
