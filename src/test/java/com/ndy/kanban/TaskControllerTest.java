package com.ndy.kanban;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndy.kanban.domain.Developer;
import com.ndy.kanban.domain.Task;
import com.ndy.kanban.service.DeveloperService;
import com.ndy.kanban.service.TaskService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.ndy.kanban.utils.Constants;
import com.ndy.kanban.utils.TaskMoveAction;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest extends ControllerTest {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DeveloperService developerService;

	@Test
	public void testfindAllTasks() throws Exception {
		mvc.perform(get("/tasks"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].title",is("Add navigation bar")))
			.andExpect(jsonPath("$[0].nbHoursReal",is(3)))
            ;		
	}
	
	@Test
	public void testfindAllTaskTypes() throws Exception {
		mvc.perform(get("/task_types"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].label", is("BUG")))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].label", is("FEATURE")));
            ;		
	}
	
	@Test
	public void testfindAllTaskStatus() throws Exception {
		mvc.perform(get("/task_status"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id", is(1)))
		    .andExpect(jsonPath("$[0].label", is("TODO")))
		    .andExpect(jsonPath("$[1].id", is(2)))
		    .andExpect(jsonPath("$[1].label", is("DOING")))
		    .andExpect(jsonPath("$[2].id", is(3)))
		    .andExpect(jsonPath("$[2].label", is("TEST")))
		    .andExpect(jsonPath("$[3].id", is(4)))
		    .andExpect(jsonPath("$[3].label", is("DONE")))
		    ;
           		
	}
	
	@Test
	public void testcreateTask() throws Exception {
		
		Assert.assertEquals(2, this.taskService.findAllTasks().size());
	 	//Create new task
	   	Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		Developer dev = this.developerService.findAllDevelopers().get(0);
		task.addDeveloper(dev);
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = mapper.writeValueAsBytes(task);
		
		mvc.perform(post("/tasks")
				.contentType(MediaType.APPLICATION_JSON).content(bytes))				
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    ;
		//Test if the task was successfully saved
		Assert.assertEquals(3, this.taskService.findAllTasks().size());
		this.taskService.deleteTask(task);	
	}
	
	
	@Test
	public void testmoveTaskTask() throws Exception {
		
		//Create new task
	   	Task task = new Task();
		task.setTitle("task test");
		task.setNbHoursReal(3);
		task.setNbHoursForecast(4);
		task.setType(this.taskService.findTaskType(Constants.TASK_TYPE_FEATURE_ID));
		Developer dev = this.developerService.findAllDevelopers().get(0);
		task.addDeveloper(dev);
		
		task = this.taskService.createTask(task);
		
		TaskMoveAction taskMoveAction = new TaskMoveAction();
		taskMoveAction.setAction(Constants.MOVE_RIGHT_ACTION);
		

		ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = mapper.writeValueAsBytes(taskMoveAction);
		
		mvc.perform(patch("/tasks/"+task.getId())
				.contentType(MediaType.APPLICATION_JSON).content(bytes))				
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    ;
		
		task = this.taskService.findTask(task.getId());
		
		Assert.assertEquals(task.getStatus(), this.taskService.findTaskStatus(Constants.TASK_STATUS_DOING_ID));
		
		this.taskService.deleteTask(task);	
		
		
			
	}
	
	
	
	

}
