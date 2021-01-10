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
import com.ndy.kanban.dao.ChangeLogRepository;
import com.ndy.kanban.dao.TaskRepository;
import com.ndy.kanban.dao.TaskStatusRepository;
import com.ndy.kanban.dao.TaskTypeRepository;
import com.ndy.kanban.domain.ChangeLog;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ChangeLogRepositoryTest {
	
	@Autowired
	private ChangeLogRepository changeLogRepository;
	
	@Autowired 
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired 
	private TaskTypeRepository taskTypeRepository;
	 
	@Autowired
	private TaskRepository taskRepository;
	
	@Test
	public void testFindAllChangeLogs() {
	
        Collection<ChangeLog> changeLogList = this.changeLogRepository.findAll();

        Assert.assertEquals(1,changeLogList.size());
	}


	@Test
	public void testSaveChangeLog() {
		
		//Create new task
		Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(5);
		task.setCreated(LocalDate.now());
		task.setType(taskTypeRepository.findById(Constants.TASK_TYPE_FEATURE_ID).orElse(null));
		task.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
		//Save task to database
		taskRepository.save(task);
		
		//Create new change log for the created task
		ChangeLog changelog = new ChangeLog();
		changelog.setOccured(LocalDate.now());
		changelog.setTask(task);
		changelog.setSourceStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
		changelog.setTargetStatus(taskStatusRepository.findById(Constants.TASK_STATUS_DOING_ID).orElse(null));
		
		//Save change log to database
		changeLogRepository.save(changelog);
		Collection<ChangeLog> changeLogList = this.changeLogRepository.findAll();
		
		//Test if the change log was successfully saved
        Assert.assertEquals(2,changeLogList.size());
        
        //Delete changelog and task from database
        changeLogRepository.delete(changelog);
        taskRepository.delete(task);
		
		
		
	}
	
	
}
