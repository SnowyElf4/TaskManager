package src.task;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private boolean done;
    private int userId; 

    public Task(int id, int userId, String title, String description) {
        this.taskId = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.done = false;  
    }


    public void markDone() {
        this.done = true;
    }

    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return this.userId; }
    public void setId(int savedId) { this.taskId = savedId; }
    public int getId() { return taskId; }
    public String setTitle(String newTitle) { return title = newTitle; }
    public String getTitle() { return title; }
    public String setDescription(String newDescription) { return description = newDescription; }
    public String getDescription() { return description; }
    public boolean isDone() { return done; }

}
