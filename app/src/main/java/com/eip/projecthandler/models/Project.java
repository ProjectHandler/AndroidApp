package com.eip.projecthandler.models;

import java.util.Date;

public class Project {
    private Long id;
    private String name;
    private Long progress;
    private String description;
    private Long duration;
    private Date dateBegin;
    private Date dateEnd;
    private String status;
    private Integer dateProgress;
    private Integer daysLeft;
    private Integer tasksProgress;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDateProgress() {
        return dateProgress;
    }

    public void setDateProgress(Integer dateProgress) {
        this.dateProgress = dateProgress;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Integer getTasksProgress() {
        return tasksProgress;
    }

    public void setTasksProgress(Integer tasksProgress) {
        this.tasksProgress = tasksProgress;
    }
}
