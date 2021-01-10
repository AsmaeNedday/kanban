package com.ndy.kanban.service;

import java.util.Collection;

import com.ndy.kanban.domain.ChangeLog;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.domain.TaskStatus;
import com.ndy.kanban.domain.TaskType;

public interface TaskService {
	
	public Collection<Task> findAllTasks();
	public Collection<TaskType> findAllTaskTypes();
	public Collection<TaskStatus> findAllTaskStatus();
	public Collection<ChangeLog> findAllChangeLogs();
	public Task findTask(Long id);
	public TaskType findTaskType(Long id );
	public TaskStatus findTaskStatus(Long id);	
	public void moveRightTask(Task task);
	public void moveLeftTask(Task task);
	public void saveTask(Task task);
	public void deleteTask(Task task);
	public void deleteChangeLog(ChangeLog changeLog);	
	
	
}
