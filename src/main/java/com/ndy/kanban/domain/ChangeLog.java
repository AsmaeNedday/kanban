package com.ndy.kanban.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChangeLog {
	
	private @Id @GeneratedValue Long id ;
	
	private LocalDate occured;
	 
	@ManyToOne
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
	public LocalDate getOccured() {
		return occured;
	}
	public void setOccured(LocalDate occured) {
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
