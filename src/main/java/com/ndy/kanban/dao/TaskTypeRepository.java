package com.ndy.kanban.dao;

import com.ndy.kanban.domain.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTypeRepository extends JpaRepository<TaskType,Long> {
	
}

