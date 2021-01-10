package com.ndy.kanban.service.impl;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ndy.kanban.dao.ChangeLogRepository;
import com.ndy.kanban.dao.TaskRepository;
import com.ndy.kanban.dao.TaskStatusRepository;
import com.ndy.kanban.dao.TaskTypeRepository;
import com.ndy.kanban.domain.ChangeLog;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.domain.TaskStatus;
import com.ndy.kanban.domain.TaskType;
import com.ndy.kanban.service.TaskService;

@Service
public class TaskServiceImpl  implements TaskService{
	@Autowired
	private TaskRepository  taskRepository;
	@Autowired
	private TaskStatusRepository taskStatusRepository;
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	@Autowired
	private ChangeLogRepository changeLogRepository;
	
	//Return all task types in database
	@Override
	@Transactional(readOnly = true)
	public Collection<TaskType> findAllTaskTypes() {
		return this.taskTypeRepository.findAll();
	}
	
	//Return all task statuses in database
	@Override
	@Transactional(readOnly = true)
	public Collection<TaskStatus> findAllTaskStatus() {
		return this.taskStatusRepository.findAll();
	}

	//Return all tasks in database
	@Override
	@Transactional(readOnly = true)
	public Collection<Task> findAllTasks() {
		return this.taskRepository.findAll();
	}
	
	//Search for a task by id and return it
	@Override
	@Transactional(readOnly = true)
	public Task findTask(Long id) {
		return this.taskRepository.findById(id).orElse(null);
	}
	
	//Move a task to the right and save changelog for this task
	@Override
	public void moveRightTask(Task task) {
		TaskStatus sourceStatus=task.getStatus(); 
		TaskStatus targetStatus=this.findTaskStatus(task.getStatus().getId() +1);
		
		//Create the change log
		ChangeLog changeLog = new ChangeLog();
		changeLog.setOccured(LocalDate.now());
		changeLog.setTask(task);
		changeLog.setSourceStatus(sourceStatus);
		changeLog.setTargetStatus(targetStatus);
		
		//Save the change log to database
		this.changeLogRepository.save(changeLog);
		task.addChangeLog(changeLog);
		
		//Change status
		task.setStatus(targetStatus);
		
		//Update the task in database
		this.taskRepository.save(task);	
			
	}
	
	//Move a task to the left and save changelog for this task
	@Override
	public void moveLeftTask(Task task) {
		TaskStatus sourceStatus=task.getStatus(); 
		TaskStatus targetStatus=this.findTaskStatus(task.getStatus().getId() -1);
		
		//Create the change log
		ChangeLog changeLog = new ChangeLog();
		changeLog.setOccured(LocalDate.now());
		changeLog.setTask(task);
		changeLog.setSourceStatus(sourceStatus);
		changeLog.setTargetStatus(targetStatus);
		
		//Save the change log to database
		this.changeLogRepository.save(changeLog);
		task.addChangeLog(changeLog);
		
		//Change status
		task.setStatus(targetStatus);
		
		//Update the task in database
		this.taskRepository.save(task);	
		
	}
	
	//Search for a task type by id and return it
	@Override
	@Transactional(readOnly = true)
	public TaskType findTaskType(Long id) {
		return this.taskTypeRepository.findById(id).orElse(null);
	}
	
	//Search for a task status by id and return it
	@Override
	@Transactional(readOnly = true)
	public TaskStatus findTaskStatus(Long id) {
		return this.taskStatusRepository.findById(id).orElse(null);
	}
	
	//Return all change logs in database
	@Override
	@Transactional(readOnly = true)
	public Collection<ChangeLog> findAllChangeLogs() {
		return this.changeLogRepository.findAll();
	}
	
	//Save task to database
	public void saveTask(Task task) {
		// TODO Auto-generated method stub
		this.taskRepository.save(task);
		
	}
	
	//Delete task from database
	@Override
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
		
	}
	
	//Delete change log from database
	@Override
	public void deleteChangeLog(ChangeLog changeLog) {
		this.changeLogRepository.delete(changeLog);
		
	}

}
