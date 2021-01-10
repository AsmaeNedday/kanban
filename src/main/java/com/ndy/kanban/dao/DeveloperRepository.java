package com.ndy.kanban.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndy.kanban.domain.Developer;

public interface DeveloperRepository extends JpaRepository<Developer,Long> {
	
}
