package com.ndy.kanban.utils;

import com.ndy.kanban.dao.ChangeLogRepository;
import com.ndy.kanban.dao.DeveloperRepository;
import com.ndy.kanban.dao.TaskRepository;
import com.ndy.kanban.dao.TaskStatusRepository;
import com.ndy.kanban.dao.TaskTypeRepository;
import com.ndy.kanban.domain.ChangeLog;
import com.ndy.kanban.domain.Developer;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.domain.TaskStatus;
import com.ndy.kanban.domain.TaskType;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class LoadDatabase {
	
	 private static Logger logger = Logger.getLogger("logger");

	@Bean
    @Profile("!test")
    CommandLineRunner initDatabase(
    		DeveloperRepository developerRepository,
    		TaskRepository taskRepository, 
    		TaskStatusRepository taskStatusRepository,
    		TaskTypeRepository taskTypeRepository
    		){
        return args -> {
           initTaskStatusAndTypes(taskStatusRepository,taskTypeRepository);
           initDevelopers(developerRepository);
           initTasks(taskRepository, taskStatusRepository,
       			 taskTypeRepository);
        };
    }
	private void initDevelopers(DeveloperRepository developerRepository) {
		
		// Add Developers to database
		Developer dev1 = new Developer();
		dev1.setFirstname("STEFEN");
		dev1.setLastname("MOROEY");
		dev1.setEmail("s.moroey@yahoo.com");
		dev1.setPassword("s123");
		dev1.setStartContract(LocalDateTime.now());
		developerRepository.save(dev1);
		logger.info(dev1+"saved");
		
		Developer dev2 = new Developer();
		dev2.setFirstname("NATALIE");
		dev2.setLastname("SALVADOR");
		dev2.setEmail("n.salvador@yahoo.com");
		dev2.setPassword("n123");
		dev2.setStartContract(LocalDateTime.now());
		developerRepository.save(dev2);
		logger.info(dev2+"saved");
		
		Developer dev3 = new Developer();
		dev3.setFirstname("BRITNEY");
		dev3.setLastname("GELLER");
		dev3.setEmail("b.geller@yahoo.com");
		dev3.setPassword("b123");
		dev3.setStartContract(LocalDateTime.now());
		developerRepository.save(dev3);
		logger.info(dev3+"saved");	
		
	}
	private void initTaskStatusAndTypes(TaskStatusRepository taskStatusRepository,
			TaskTypeRepository taskTypeRepository) {
		
		// Add TaskTypes and TaskStatus to database
		TaskStatus todo =new TaskStatus(Constants.TASK_STATUS_TODO_ID,Constants.TASK_STATUS_TODO_LABEL);
		taskStatusRepository.save(todo);
		logger.info(todo +"saved");	
		
		TaskStatus doing =new TaskStatus(Constants.TASK_STATUS_DOING_ID,Constants.TASK_STATUS_DOING_LABEL);
		taskStatusRepository.save(doing);
		logger.info(doing+"saved");	
		
		TaskStatus test =new TaskStatus(Constants.TASK_STATUS_TEST_ID,Constants.TASK_STATUS_TEST_LABEL);
		taskStatusRepository.save(test);
		logger.info(test+"saved");	
		
		TaskStatus done =new TaskStatus(Constants.TASK_STATUS_DONE_ID,Constants.TASK_STATUS_DONE_LABEL);
		taskStatusRepository.save(done);
		logger.info(done+"saved");	
		
		TaskType feature = new TaskType(Constants.TASK_TYPE_FEATURE_ID,Constants.TASK_TYPE_FEATURE_LABEL);
		taskTypeRepository.save(feature);
		logger.info(feature+"saved");	
		
		TaskType bug = new TaskType(Constants.TASK_TYPE_BUG_ID,Constants.TASK_TYPE_BUG_LABEL);
		taskTypeRepository.save(bug);
		logger.info(bug+"saved");	
		
	}
	
	private void initTasks(TaskRepository taskRepository, TaskStatusRepository taskStatusRepository,
			TaskTypeRepository taskTypeRepository) {
		Task task1 = new Task();
		task1.setTitle("Add navigation bar");
		task1.setNbHoursReal(3);
		task1.setNbHoursForecast(4);
		task1.setCreated(LocalDateTime.now());
		task1.setType(taskTypeRepository.findById(Constants.TASK_TYPE_FEATURE_ID).orElse(null));
		task1.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
		taskRepository.save(task1);
		logger.info(task1+"saved");			

		
	}
	
	 @Bean
	    @Profile("test")
	 CommandLineRunner initTestDatabase(
	    		DeveloperRepository developerRepository,
	    		TaskRepository taskRepository, 
	    		TaskStatusRepository taskStatusRepository,
	    		TaskTypeRepository taskTypeRepository,
	    		ChangeLogRepository changeLogRepository
	    		){
	        return args -> {
	       initTaskStatusAndTypes(taskStatusRepository,taskTypeRepository);
	        
	        //Adding developers for test
	        Developer dev1_test = new Developer();
	   		dev1_test.setFirstname("dev1_firstname");
	   		dev1_test.setLastname("dev1_lastname");
	   		dev1_test.setEmail("dev1@yahoo.com");
	   		dev1_test.setPassword("dev1_password");
	   		dev1_test.setStartContract(LocalDateTime.now());
	   		developerRepository.save(dev1_test);
	   		logger.info(dev1_test+"saved");
	   		
	   		Developer dev2_test = new Developer();
	   		dev2_test.setFirstname("dev2_firstname");
	   		dev2_test.setLastname("dev2_lastname");
	   		dev2_test.setEmail("dev2@yahoo.com");
	   		dev2_test.setPassword("dev2_password");
	   		dev2_test.setStartContract(LocalDateTime.now());
	   		developerRepository.save(dev2_test);
	   		logger.info(dev2_test+"saved");
	   		
	   		//Adding tasks for test
	   		Task task1 = new Task();
			task1.setTitle("Add navigation bar");
			task1.setNbHoursReal(3);
			task1.setNbHoursForecast(4);
			task1.setCreated(LocalDateTime.now());
			task1.setType(taskTypeRepository.findById(Constants.TASK_TYPE_FEATURE_ID).orElse(null));
			task1.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
			taskRepository.save(task1);
			logger.info(task1+"saved");			

	   		Task task2 = new Task();
			task2.setTitle("Fix pagination bug");
			task2.setNbHoursReal(3);
			task2.setNbHoursForecast(2);
			task2.setCreated(LocalDateTime.now());
			task2.setType(taskTypeRepository.findById(Constants.TASK_TYPE_BUG_ID).orElse(null));
			task2.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
			taskRepository.save(task2);
			logger.info(task2+"saved");
			
			//Adding changelog for test
			ChangeLog changelog1 = new ChangeLog();
			changelog1.setOccured(LocalDateTime.now());
			changelog1.setTask(task1);
			changelog1.setSourceStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
			changelog1.setTargetStatus(taskStatusRepository.findById(Constants.TASK_STATUS_DOING_ID).orElse(null));
			changeLogRepository.save(changelog1);
			logger.info(changelog1+"saved");		
	           
	        };
	    }
	    


}
