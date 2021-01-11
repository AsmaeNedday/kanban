package com.ndy.kanban.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.domain.TaskStatus;
import com.ndy.kanban.domain.TaskType;
import com.ndy.kanban.service.TaskService;
import com.ndy.kanban.utils.Constants;
import com.ndy.kanban.utils.TaskMoveAction;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600,methods= {RequestMethod.GET, RequestMethod.POST, 
		RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE
})
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/tasks")
	Collection<Task> findAllTasks(){
		return taskService.findAllTasks();		
	} 
	
	@GetMapping("/task_types")
	Collection<TaskType> findAllTaskTypes() {
		return this.taskService.findAllTaskTypes();
	}
	
	@GetMapping("/task_status")
	Collection<TaskStatus> findAllTaskStatus() {
		return this.taskService.findAllTaskStatus();
	}
	
	@PostMapping("/tasks")
	Task createTask(@Valid @RequestBody Task task) {
		return this.taskService.createTask(task);
	}
	
	@PatchMapping("/tasks/{id}")
	Task moveTask(@RequestBody TaskMoveAction taskMoveAction, @PathVariable Long id) {
		
		Task task = this.taskService.findTask(id);
				
		if (Constants.MOVE_LEFT_ACTION.equals(taskMoveAction.getAction())) { 
		
			task = this.taskService.moveLeftTask(task);
		}
		else if (Constants.MOVE_RIGHT_ACTION.equals(taskMoveAction.getAction())) {
			
			task = this.taskService.moveRightTask(task);
		}
		else {
			throw new IllegalStateException();
		}
		
		return task;
	}

}
