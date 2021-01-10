package com.ndy.kanban.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndy.kanban.domain.Task;

public interface TaskRepository  extends JpaRepository<Task,Long> {

}
