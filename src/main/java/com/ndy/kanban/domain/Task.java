package com.ndy.kanban.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task {
	
	@Id 
	@GeneratedValue
	private Long id;	
	private String title;
	private Integer nbHoursReal;
	private Integer nbHoursForecast;
	private LocalDate created;
	
	@ManyToOne
	private TaskType type;
	
	@ManyToOne
	private TaskStatus status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Developer> developers;
	
	@OneToMany(mappedBy="task",cascade= {CascadeType.ALL},orphanRemoval=true)
	@JsonIgnore
	private Set<ChangeLog> changeLogs;
	
	public Task() {
		this.developers=new HashSet<>();
		this.changeLogs=new HashSet<>(); 
	}
	
	//Assign task to developer
	public void addDeveloper(Developer developer) {
		developer.getTasks().add(this);
		this.developers.add(developer);
	}
	
	//Add changeLog to task
	public void addChangeLog(ChangeLog changeLog) {
		changeLog.setTask(this);
		this.changeLogs.add(changeLog);
	}
	
	public void clearChangeLogs() {
		for (ChangeLog changeLog : this.changeLogs) {
			changeLog.setTask(null);
		}
		//clear the list
		this.changeLogs.clear();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNbHoursReal() {
		return nbHoursReal;
	}
	public void setNbHoursReal(Integer nbHoursReal) {
		this.nbHoursReal = nbHoursReal;
	}
	public Integer getNbHoursForecast() {
		return nbHoursForecast;
	}
	public void setNbHoursForecast(Integer nbHoursForecast) {
		this.nbHoursForecast = nbHoursForecast;
	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public TaskType getType() {
		return type;
	}
	public void setType(TaskType type) {
		this.type = type;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public Set<Developer> getDevelopers() {
		return developers;
	}
	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}

	public Set<ChangeLog> getChangeLogs() {
		return changeLogs;
	}

	public void setChangeLogs(Set<ChangeLog> changeLogs) {
		this.changeLogs = changeLogs;
	}
	
	

}
