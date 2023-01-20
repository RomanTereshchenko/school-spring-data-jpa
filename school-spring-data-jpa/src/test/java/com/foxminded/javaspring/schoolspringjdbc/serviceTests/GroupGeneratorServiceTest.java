package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.service.GroupGeneratorService;

@SpringBootTest
class GroupGeneratorServiceTest {
	
	private GroupGeneratorService groupGeneratorService;

	
	@Autowired
	GroupGeneratorServiceTest(GroupGeneratorService groupGeneratorService) {
		this.groupGeneratorService = groupGeneratorService;
	}
	
	@Test
	void testGenerateNGroups() {
		assertNotNull(groupGeneratorService.generateNGroups(10));
		assertEquals(10, groupGeneratorService.generateNGroups(10).size());
	}

}
