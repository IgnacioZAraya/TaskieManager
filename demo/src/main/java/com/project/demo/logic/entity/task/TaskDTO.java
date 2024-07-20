package com.project.demo.logic.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;

import java.util.Date;

public class TaskDTO {
    private Long id;
    private String name;
    private Integer priority;
    private String description;
    private Date startDate;

    private Date endDate;

    private boolean visible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TaskDTO(Long id, String name, Integer priority, String description, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
