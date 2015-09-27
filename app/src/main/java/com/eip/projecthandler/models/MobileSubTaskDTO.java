package com.eip.projecthandler.models;

public class MobileSubTaskDTO {

    private String description;
    private Task parentTask;
    private User lastUserActivity;
    private boolean validated;
    private boolean taken;

    public MobileSubTaskDTO() {
        this.description = "test 1";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public User getLastUserActivity() {
        return lastUserActivity;
    }

    public void setLastUserActivity(User lastUserActivity) {
        this.lastUserActivity = lastUserActivity;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
