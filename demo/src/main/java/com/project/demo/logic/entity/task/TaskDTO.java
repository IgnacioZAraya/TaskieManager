package com.project.demo.logic.entity.task;

import java.util.Date;

public class TaskDTO {
    private Long id;
    private String name;
    private String priority;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean visible;

    private String recurrent;

    private Long repeatTimes;


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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
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

    public String getRecurrent() {
        return recurrent;
    }

    public void setRecurrent(String recurrent) {
        this.recurrent = recurrent;
    }

    public Long getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Long repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public TaskDTO(Long id, String name, String priority, String description, Date startDate, Date endDate, Boolean visible, String recurrent, Long repeatTimes) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visible = visible;
        this.recurrent = recurrent;
        this.repeatTimes = repeatTimes;
    }

}
