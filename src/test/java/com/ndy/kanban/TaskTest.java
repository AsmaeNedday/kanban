package com.ndy.kanban;

import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.ndy.kanban.dao.TaskStatusRepository;
import com.ndy.kanban.domain.ChangeLog;
import com.ndy.kanban.domain.Developer;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TaskTest {
	
	@Autowired
	TaskStatusRepository taskStatusRepository;

	@Test
	public void testAddDeveloper() {
		
		//Create new developer
		Developer dev1_test = new Developer();
	   	dev1_test.setFirstname("dev1_firstname");
	   	dev1_test.setLastname("dev1_lastname");
	   	dev1_test.setEmail("dev1@yahoo.com");
	   	dev1_test.setPassword("dev1_password");
	   	dev1_test.setStartContract(LocalDateTime.now());
	   	
	   	//Create new task
	   	Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setCreated(LocalDateTime.now());
		
		Assert.assertEquals(0,task.getDevelopers().size());
		Assert.assertEquals(0,dev1_test.getTasks().size());
		
		//Add developer to task
		task.addDeveloper(dev1_test);
		
		//Test
		Assert.assertEquals(1,task.getDevelopers().size());
		Assert.assertEquals(1,dev1_test.getTasks().size());
	
	}

	@Test
	public void testAddChangeLog() {
		Task task = new Task();
		
		Assert.assertEquals(0,task.getChangeLogs().size());
		
		ChangeLog changelog = new ChangeLog();
		changelog.setOccured(LocalDateTime.now());
		changelog.setSourceStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
		changelog.setTargetStatus(taskStatusRepository.findById(Constants.TASK_STATUS_DOING_ID).orElse(null));
		
		//Add change log to a task
		task.addChangeLog(changelog);
		
		//Test
		Assert.assertEquals(1,task.getChangeLogs().size());
		Assert.assertEquals(task,changelog.getTask());
	}

	@Test
	public void testClearChangeLogs() {
		//Create task and changelog for the task
        Task task = new Task();
        ChangeLog changelog=new ChangeLog();
        changelog.setTask(task);
        task.getChangeLogs().add(changelog);
		
		Assert.assertEquals(1,task.getChangeLogs().size());	
		Assert.assertEquals(task,changelog.getTask());	
		
		//Clear
		task.clearChangeLogs();
		
		//Test
		Assert.assertEquals(0,task.getChangeLogs().size());	
		Assert.assertEquals(null,changelog.getTask());
		
		
		
	}

}
