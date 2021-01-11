package com.ndy.kanban;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.domain.TaskStatus;
import com.ndy.kanban.domain.TaskType;
import com.ndy.kanban.service.TaskService;
import com.ndy.kanban.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TaskServiceTest {
	
	@Autowired
	private TaskService taskService;

	@Test
	public void testFindAllTasks() {
		 Assert.assertEquals(2,taskService.findAllTasks().size());
	}

	@Test
	public void testFindAllTaskTypes() {
		 Assert.assertEquals(2, this.taskService.findAllTaskTypes().size());
	}

	@Test
	public void testFindAllTaskStatus() {
		Assert.assertEquals(4, this.taskService.findAllTaskStatus().size());
	}

	@Test
	public void testFindTask() {
		
		//Get tasks from database
		Collection<Task> taskList = this.taskService.findAllTasks();
		
		//Get the first task
		Task task1 = taskList.iterator().next();
		Task task_found = this.taskService.findTask(task1.getId());
		
		//Compare task_found properties with properties of the first task saved in database
		Assert.assertEquals("Add navigation bar",task_found.getTitle());
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_ID,task_found.getType().getId());
		Assert.assertEquals(Constants.TASK_STATUS_TODO_ID,task_found.getStatus().getId());				
	}
	
	@Test
	public void testCreateTask() {
		
		Assert.assertEquals(2,this.taskService.findAllTasks().size());
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		
		this.taskService.createTask(task);
		
		Assert.assertEquals(3,taskService.findAllTasks().size());
		
		this.taskService.deleteTask(task);
	}
	
	@Test
	public void testChangeTaskStatusk() {
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		task = this.taskService.createTask(task);
				
		Assert.assertEquals(0,task.getChangeLogs().size());
		Assert.assertEquals(1,this.taskService.findAllChangeLogs().size());
		
		//Change status to done
		task = this.taskService.changeTaskStatus(task,this.taskService.findTaskStatus(Constants.TASK_STATUS_DONE_ID));
		
		//Test if status has changed to DONE
		Assert.assertEquals(task.getStatus(),this.taskService.findTaskStatus(Constants.TASK_STATUS_DONE_ID));
		Assert.assertEquals(1,task.getChangeLogs().size());
		
		//Test if changelog has been saved
		Assert.assertEquals(2,this.taskService.findAllChangeLogs().size());
		
		//Delete task and changelog from database
		taskService.deleteChangeLog(task.getChangeLogs().iterator().next());
		taskService.deleteTask(task);	
			
	}
	

	@Test
	public void testMoveRightTask() {
		
		//Create a new task 
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		task = this.taskService.createTask(task);
		
		Assert.assertEquals(0,task.getChangeLogs().size());
		Assert.assertEquals(1,this.taskService.findAllChangeLogs().size());	
		
		//Move Right Task
		task = this.taskService.moveRightTask(task);
		
		//Test if status has changed to DOING
		Assert.assertEquals(task.getStatus().getLabel(),this.taskService.findTaskStatus(Constants.TASK_STATUS_DOING_ID).getLabel());
			
		Assert.assertEquals(1,task.getChangeLogs().size());
		
		//Test if changelog has been saved
		Assert.assertEquals(2,this.taskService.findAllChangeLogs().size());
		
		//Delete task and changelog from database
		taskService.deleteChangeLog(task.getChangeLogs().iterator().next());
		taskService.deleteTask(task);
						
	}

	@Test
	public void testMoveLeftTask() {
		
		//Create a task with status DONE
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		task = this.taskService.createTask(task);
				
		Assert.assertEquals(0,task.getChangeLogs().size());
		Assert.assertEquals(1,taskService.findAllChangeLogs().size());
		
		//Change status to done
		task = this.taskService.changeTaskStatus(task,this.taskService.findTaskStatus(Constants.TASK_STATUS_DONE_ID));
		
		//Move Left Task
		task = this.taskService.moveLeftTask(task);
		
		//Test if status has changed to DOING
		Assert.assertEquals(task.getStatus(),this.taskService.findTaskStatus(Constants.TASK_STATUS_TEST_ID));
		Assert.assertEquals(2,task.getChangeLogs().size());
		
		//Test if changelog has been saved
		Assert.assertEquals(3,this.taskService.findAllChangeLogs().size());
		
		//Delete task and changelog from database
		this.taskService.deleteChangeLog(task.getChangeLogs().iterator().next());
		this.taskService.deleteTask(task);
				
		Assert.assertEquals(1,this.taskService.findAllChangeLogs().size());
	}
	

	@Test
	public void testFindTaskType() {
		TaskType taskType = this.taskService.findTaskType(2L);
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_ID,taskType.getId());
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_LABEL,taskType.getLabel());
	}

	@Test
	public void testFindTaskStatus() {
		TaskStatus taskStatus = this.taskService.findTaskStatus(2L);
		Assert.assertEquals(Constants.TASK_STATUS_DOING_ID,taskStatus.getId());
		Assert.assertEquals(Constants.TASK_STATUS_DOING_LABEL,taskStatus.getLabel());
	}

}
