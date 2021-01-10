package com.ndy.kanban.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ndy.kanban.dao.DeveloperRepository;
import com.ndy.kanban.domain.Developer;
import com.ndy.kanban.service.DeveloperService;

@Service
public class DeveloperServiceImpl implements DeveloperService {
	
	@Autowired
	private DeveloperRepository developerRepository ;

	//Returns all developers in database
	@Override
	@Transactional(readOnly=true)
	public List<Developer> findAllDevelopers() {
		return this.developerRepository.findAll();
	}

	
	
	

}
