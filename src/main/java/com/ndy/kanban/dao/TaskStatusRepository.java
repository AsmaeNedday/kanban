package com.ndy.kanban.dao;

import com.ndy.kanban.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus,Long> {
}
