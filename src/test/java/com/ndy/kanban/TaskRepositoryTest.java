package com.ndy.kanban;

import java.time.LocalDate;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.ndy.kanban.dao.TaskRepository;
import com.ndy.kanban.dao.TaskStatusRepository;
import com.ndy.kanban.dao.TaskTypeRepository;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TaskRepositoryTest {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired 
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired 
	private TaskTypeRepository taskTypeRepository;
	
	@Test 
	public void testFindAllTasks() {
		
        Collection<Task> tasks = this.taskRepository.findAll();
        Assert.assertEquals(2, tasks.size());
    }

	@Test
	public void testSaveTask() {
		
		//Create new task
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setCreated(LocalDate.now());
		task.setType(this.taskTypeRepository.findById(Constants.TASK_TYPE_FEATURE_ID).orElse(null));
		task.setStatus(this.taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
		
		//Save task 
		taskRepository.save(task);
		
		Collection<Task> tasks = this.taskRepository.findAll();
		
		//Test if the task was successfully saved
		Assert.assertEquals(3, tasks.size());
		
		//Delete task from database
		taskRepository.delete(task);
	}
	

}
