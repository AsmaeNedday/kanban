package com.ndy.kanban.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.service.TaskService;

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

}
