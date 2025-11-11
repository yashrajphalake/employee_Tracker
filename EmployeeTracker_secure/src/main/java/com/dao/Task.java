package com.dao;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing a task in the PEPT system.
 * Mirrors the 'tasks' table structure.
 */
public class Task {
    private int taskId;
    private String title;
    private String description;
    private String managerId;
    private String assignedToId;
    private String assignedToName; // Helper field for display
    private Date deadline;
    private String status; // Pending, In Progress, Complete
    private Date createdAt;
    private Date updatedAt;

    // Default Constructor
    public Task() {}

    // --- Getters and Setters ---

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public String getAssignedToId() { return assignedToId; }
    public void setAssignedToId(String assignedToId) { this.assignedToId = assignedToId; }

    public String getAssignedToName() { return assignedToName; }
    public void setAssignedToName(String assignedToName) { this.assignedToName = assignedToName; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}