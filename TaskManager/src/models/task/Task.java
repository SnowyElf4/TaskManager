package src.models.task;

import src.interfaces.Identifiable;

public class Task implements Identifiable {
    private int taskId;
    private String title;
    private String description;
    private boolean done;
    private int userId; 

    public Task(int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.done = false;  
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
    public String getDescription() { return description; }
    public String getTitle() { return title; }
    public int getUserId() { return this.userId; }

    // setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setDescription(String newDescription) { description = newDescription; }
}