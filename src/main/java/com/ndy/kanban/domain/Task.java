package com.ndy.kanban.domain;

import java.time.LocalDateTime;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Task {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be empty")
	private String title;
	
	@NotNull(message = "NbHoursReal cannot be null")
	@Min(value = 0, message = "NbHoursReal should not be less than 0")
    @Max(value = 100, message = "NbHoursReal should not be greater than 50")
	private Integer nbHoursReal;
	
	@NotNull(message = "NbHoursForecast cannot be null")
	@Min(value = 0, message = "NbHoursForecast should not be less than 0")
    @Max(value = 100, message = "NbHoursForecast should not be greater than 50")
	private Integer nbHoursForecast;
	
	private LocalDateTime created;
	
	@ManyToOne
	@Valid
	private TaskType type;
	
	@ManyToOne
	private TaskStatus status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"tasks", "password", "startContract"})
	@NotEmpty(message = "Developers cannot be empty")
	private Set<Developer> developers;
	
	@OneToMany(mappedBy="task",cascade= {CascadeType.ALL},orphanRemoval=true)
	@JsonIgnoreProperties("task")
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
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
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
