package com.eip.projecthandler.models;

import com.eip.projecthandler.enums.TicketStatus;

import java.util.Date;
import java.util.List;

public class Ticket {

    private Long id;
    private String title;
    private String text;
    private TicketStatus ticketStatus;
    private Long projectId;
    private TicketTracker ticketTracker;
    private TicketPriority ticketPriority;
    private List<TicketMessage> ticketMessage;
    private Date createdAt;
    private Date updatedAt;

    public Ticket() {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public TicketTracker getTicketTracker() {
        return ticketTracker;
    }

    public void setTicketTracker(TicketTracker ticketTracker) {
        this.ticketTracker = ticketTracker;
    }

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public List<TicketMessage> getTicketMessage() {
        return ticketMessage;
    }

    public void setTicketMessage(List<TicketMessage> ticketMessage) {
        this.ticketMessage = ticketMessage;
    }

    public void addTicketMessage(TicketMessage ticketMessage) {
        this.ticketMessage.add(ticketMessage);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
