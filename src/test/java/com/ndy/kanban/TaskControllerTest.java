package com.ndy.kanban;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest extends ControllerTest {
	
	@Test
	public void testGetTasks() throws Exception {
		mvc.perform(get("/tasks"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].title",is("Add navigation bar")))
			.andExpect(jsonPath("$[0].nbHoursReal",is(3)))
            ;

		
	}

}
