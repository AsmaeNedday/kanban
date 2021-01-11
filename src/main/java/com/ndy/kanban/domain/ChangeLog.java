package com.ndy.kanban.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class ChangeLog {
	
	private @Id @GeneratedValue Long id ;
	
	private LocalDateTime occured;
	 
	@ManyToOne
	@JsonIgnoreProperties("changeLogs")
	private Task task;
	 
	@ManyToOne
	private TaskStatus sourceStatus;
	 
	@ManyToOne
	private TaskStatus targetStatus;
	 
	
	public ChangeLog() {
		
	}
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getOccured() {
		return occured;
	}
	public void setOccured(LocalDateTime occured) {
		this.occured = occured;
	}
	
	public TaskStatus getSourceStatus() {
		return sourceStatus;
	}
	public void setSourceStatus(TaskStatus sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	public TaskStatus getTargetStatus() {
		return targetStatus;
	}
	public void setTargetStatus(TaskStatus targetStatus) {
		this.targetStatus = targetStatus;
	}
	 

}
