package com.ndy.kanban.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ndy.kanban.domain.Developer;
import com.ndy.kanban.service.DeveloperService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600,methods= {RequestMethod.GET, RequestMethod.POST, 
		RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE
})
public class DeveloperController {
	
	@Autowired
	private DeveloperService developerService;
	
	@GetMapping("/developers")
	List <Developer> findAllDevelopers(){
		return developerService.findAllDevelopers();
		
	} 

}
