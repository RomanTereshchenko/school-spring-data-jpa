package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JPAGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private JPAGroupDao jdbcGroupDao;

	@InjectMocks
	private GroupService groupService;

	@Test
	void testFindGroupsByStudentsCount() {
		List<Group> selectedGroups = new ArrayList<>();
		selectedGroups.add(new Group(1, "tt-11"));
		Mockito.when(jdbcGroupDao.selectGroupsByStudentsCount(anyInt())).thenReturn(selectedGroups);
		Mockito.when(scannerUtil.scanInt()).thenReturn(20);
		groupService.findGroupsByStudentsCount();
		Mockito.verify(jdbcGroupDao).selectGroupsByStudentsCount(20);
		Mockito.verify(scannerUtil).scanInt();
	}

}
