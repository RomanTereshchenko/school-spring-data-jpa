package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.model.Group;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupGeneratorService {

	private Random random;
	
	@Autowired
	public GroupGeneratorService(Random random) {
		this.random = random;
	}

	@Transactional
	public List<Group> generateNGroups(int countToGenerate) {
		List<Group> groupsLocal = new ArrayList<>();
			for (int i = 0; i < countToGenerate; i++) {
			Group group = new Group();
			group.setGroupName(generateGroupName());
			groupsLocal.add(group);
		}
		log.info("Groups generated");
		return groupsLocal;
	}

	private String generateGroupName() {
		return (new StringBuilder().append(generateRandomChar())
				.append(generateRandomChar()) + "-" + generateRandomInt() + generateRandomInt())
				.toString();
	}

	private int generateRandomInt() {
		return random.nextInt(10);
	}

	private char generateRandomChar() {
		return (char) (random.nextInt(26) + 'a');
	}

}
