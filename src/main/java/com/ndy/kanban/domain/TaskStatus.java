package com.ndy.kanban.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TaskStatus {

    private @Id Long id ;
    private String label;

    public TaskStatus(Long id,String label){
        this.id=id;
        this.label=label;
    }
    public TaskStatus(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
