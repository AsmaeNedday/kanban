package com.ndy.kanban.service.impl;

import java.time.LocalDateTime;
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
import com.ndy.kanban.utils.Constants;

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
	
	//Delete task from database
	@Override
	public void deleteTask(Task task) {
		
        task = this.taskRepository.save(task);
		task.clearChangeLogs();
		this.taskRepository.delete(task);
		
	}
	
	//Delete change log from database
	@Override
	public void deleteChangeLog(ChangeLog changeLog) {
		
		this.changeLogRepository.delete(changeLog);
		
	}
	
	@Override
	@Transactional
	public Task createTask(Task task) {
		
		TaskStatus taskStatus = this.findTaskStatus(Constants.TASK_STATUS_TODO_ID);
		task.setStatus(taskStatus);
		task.setCreated(LocalDateTime.now());
        return this.taskRepository.save(task);
	}
	
    //Change the status of a given task to a given status target
	@Override
	@Transactional
	public Task changeTaskStatus(Task task, TaskStatus targetStatus) {

		task = this.taskRepository.save(task);
		
		TaskStatus sourceStatus=task.getStatus(); 
		
		//Create the change log
		ChangeLog changeLog = new ChangeLog();
		changeLog.setOccured(LocalDateTime.now());
		changeLog.setSourceStatus(sourceStatus);
		changeLog.setTargetStatus(targetStatus);
		
		
		task.addChangeLog(changeLog);
		
		//Save the change log to database
		this.changeLogRepository.save(changeLog);
		
		//Change status
		task.setStatus(targetStatus);
		
		//Update the task in database
		this.taskRepository.save(task);	
		
		return task;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean displayMoveRightForTask(Task task) {
		
		return !task.getStatus().equals(this.findTaskStatus(Constants.TASK_STATUS_DONE_ID));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean displayMoveLeftForTask(Task task) {
		
		return !task.getStatus().equals(this.findTaskStatus(Constants.TASK_STATUS_TODO_ID));
	}
	
	@Override
	@Transactional
	public Task moveRightTask(Task task) {
		
		TaskStatus taskStatus = task.getStatus();
		TaskStatus todoStatus = this.findTaskStatus(Constants.TASK_STATUS_TODO_ID);
        TaskStatus doingStatus = this.findTaskStatus(Constants.TASK_STATUS_DOING_ID);		
		TaskStatus testStatus = this.findTaskStatus(Constants.TASK_STATUS_TEST_ID); 
		TaskStatus targetStatus = null;
		if (taskStatus.equals(todoStatus) || taskStatus.equals(doingStatus) || taskStatus.equals(testStatus)) {
			
			targetStatus=this.findTaskStatus(taskStatus.getId() +1);
		}
		else {
			throw new IllegalStateException();
		}
		
		return this.changeTaskStatus(task, targetStatus);
	}
	
	@Override
	@Transactional
	public Task moveLeftTask(Task task) {
		
		TaskStatus taskStatus = task.getStatus();
        TaskStatus doingStatus = this.findTaskStatus(Constants.TASK_STATUS_DOING_ID);		
		TaskStatus testStatus = this.findTaskStatus(Constants.TASK_STATUS_TEST_ID); 
		TaskStatus doneStatus = this.findTaskStatus(Constants.TASK_STATUS_DONE_ID);
		TaskStatus targetStatus = null;
		if (taskStatus.equals(doneStatus) || taskStatus.equals(doingStatus) || taskStatus.equals(testStatus)) {
			
			targetStatus=this.findTaskStatus(taskStatus.getId() - 1);
		}
		else {
			throw new IllegalStateException();
		}
		
		return this.changeTaskStatus(task, targetStatus);
	}

}
