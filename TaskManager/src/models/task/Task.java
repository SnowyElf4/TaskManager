package src.models.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.interfaces.Identifiable;

public class Task implements Identifiable {
    private int taskId;
    private String title;
    private String description;
    private boolean done;
    private int userId;
    private String createdAt;

    public Task(int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.done = false;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' hh:mm a"));
    }

    public void markDone() {
        this.done = true;
    }

    @Override
    public int getId() { return taskId; }

    @Override
    public void setId(int savedId) { this.taskId = savedId; }
    
    public boolean isDone() { return done; }

    // getters
    public String getDescription() { return this.description; }
    public String getTitle() { return this.title; }
    public int getUserId() { return this.userId; }
    public String getCreatedAt() { return this.createdAt; }

    // setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setDescription(String newDescription) { description = newDescription; }
}