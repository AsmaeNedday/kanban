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
		 Assert.assertEquals(2, taskService.findAllTaskTypes().size());
	}

	@Test
	public void testFindAllTaskStatus() {
		Assert.assertEquals(3, taskService.findAllTaskStatus().size());
	}

	@Test
	public void testFindTask() {
		
		//Get tasks from database
		Collection<Task> taskList = taskService.findAllTasks();
		
		//Get the first task
		Task task1 = taskList.iterator().next();
		Task task_found = taskService.findTask(task1.getId());
		
		//Compare task_found properties with properties of the first task saved in database
		Assert.assertEquals("Add navigation bar",task_found.getTitle());
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_ID,task_found.getType().getId());
		Assert.assertEquals(Constants.TASK_STATUS_TODO_ID,task_found.getStatus().getId());				
	}

	@Test
	public void testMoveRightTask() {
		
		//Create a task with statusTODO
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setCreated(LocalDate.now());
		task.setType(taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		task.setStatus(taskService.findTaskStatus(Constants.TASK_STATUS_TODO_ID));
		taskService.saveTask(task);
		
		Assert.assertEquals(0,task.getChangeLogs().size());
		Assert.assertEquals(1,taskService.findAllChangeLogs().size());	
		
		//Move Right Task
		taskService.moveRightTask(task);
		
		//Test if status has changed to DOING
		Assert.assertEquals(Constants.TASK_STATUS_DOING_ID,task.getStatus().getId());
			
		Assert.assertEquals(1,task.getChangeLogs().size());
		
		//Test if changelog has been saved
		Assert.assertEquals(2,taskService.findAllChangeLogs().size());
		
		//Delete task and changelog from database
		taskService.deleteChangeLog(task.getChangeLogs().iterator().next());
		taskService.deleteTask(task);
		
		Assert.assertEquals(1,taskService.findAllChangeLogs().size());				
	}

	@Test
	public void testMoveLeftTask() {
		
		//Create a task with status DONE
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setCreated(LocalDate.now());
		task.setType(taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		task.setStatus(taskService.findTaskStatus(Constants.TASK_STATUS_DONE_ID));
		taskService.saveTask(task);
		
		Assert.assertEquals(0,task.getChangeLogs().size());
		Assert.assertEquals(1,taskService.findAllChangeLogs().size());	
		
		//Move Left Task
		taskService.moveLeftTask(task);
		
		//Test if status has changed to DOING
		Assert.assertEquals(Constants.TASK_STATUS_DOING_ID,task.getStatus().getId());
		Assert.assertEquals(1,task.getChangeLogs().size());
		
		//Test if changelog has been saved
		Assert.assertEquals(2,taskService.findAllChangeLogs().size());
		
		//Delete task and changelog from database
		taskService.deleteChangeLog(task.getChangeLogs().iterator().next());
		taskService.deleteTask(task);
				
		Assert.assertEquals(1,taskService.findAllChangeLogs().size());
	}

	@Test
	public void testFindTaskType() {
		TaskType taskType = taskService.findTaskType(2L);
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_ID,taskType.getId());
		Assert.assertEquals(Constants.TASK_TYPE_FEATURE_LABEL,taskType.getLabel());
	}

	@Test
	public void testFindTaskStatus() {
		TaskStatus taskStatus = taskService.findTaskStatus(2L);
		Assert.assertEquals(Constants.TASK_STATUS_DOING_ID,taskStatus.getId());
		Assert.assertEquals(Constants.TASK_STATUS_DOING_LABEL,taskStatus.getLabel());
	}

}
