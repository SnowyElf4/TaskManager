package TaskManager.service;
import TaskManager.task.*;
import java.util.ArrayList; import java.util.List;

public class TaskService {
    private IdGenerator idGen = new IdGenerator();
    private List<Task> tasks = new ArrayList<Task>();

    public Task createTask(int userId, String title, String description) { 
        int id = idGen.generateId();
        Task task = new Task(id, userId, title, description);
        tasks.add(task);

        return task;
    }

    public Task markTaskDone(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == id) {
                task.markDone();
                return task;
            }
        }
        return null;
    }

    public List<Task> getTaskByUser(int userId) {
        List<Task> userTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getUserId() == userId) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }
}
