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
	
	public Task moveRightTask(Task task);
	
	public Task moveLeftTask(Task task);
	
    public boolean displayMoveRightForTask(Task task);
	
	public boolean displayMoveLeftForTask(Task task);
	
	public void deleteTask(Task task);
	
	public void deleteChangeLog(ChangeLog changeLog);
	
	public Task createTask(Task task);

	Task changeTaskStatus(Task task, TaskStatus targetStatus);	
	
	
}
