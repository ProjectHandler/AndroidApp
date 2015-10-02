package com.eip.projecthandler.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Task {

    private Long id;
    private String name;
    private String description;
    private Long progress;
    private Long level;
    private Long duration;
    private Date startingDate;
    private Date endingDate;
    private String status;
    private List<User> users;
    private Long row;
    private Set<Task> dependtasks;
    private Set<MobileSubTaskDTO> mobileSubTaskDTO;

    public Task() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Set<Task> getDependtasks() {
        return dependtasks;
    }

    public void setDependtasks(Set<Task> dependtasks) {
        this.dependtasks = dependtasks;
    }

    public Set<MobileSubTaskDTO> getMobileSubTaskDTO() {
        return mobileSubTaskDTO;
    }

    public void setMobileSubTaskDTO(Set<MobileSubTaskDTO> mobileSubTaskDTO) {
        this.mobileSubTaskDTO = mobileSubTaskDTO;
    }

}
