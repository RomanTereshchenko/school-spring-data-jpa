package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.dao.GroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
@Transactional
@SpringBootTest
class GroupServiceTest {

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private GroupDao groupDao;

	@InjectMocks
	private GroupService groupService;

	@Test
	void testFindGroupsByStudentsCount() {
		List<Group> testSelectedGroups = new ArrayList<>();
		testSelectedGroups.add(new Group(1, "tt-11"));
		Mockito.when(scannerUtil.scanInt()).thenReturn(20);
		Mockito.when(groupDao.findByStudentsCount(20)).thenReturn(testSelectedGroups);
		List<Group> result = groupService.findGroupsByStudentsCount();
		assertEquals("tt-11", result.get(0).getGroupName());
//		verify(groupDao).findByStudentsCount(20);
		verify(scannerUtil).scanInt();
	}

}
