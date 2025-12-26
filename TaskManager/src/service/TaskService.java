package src.service;
import java.util.ArrayList; import java.util.List;

import src.task.*;

public class TaskService {
    private IdGenerator idGen = new IdGenerator();
    private StorageService storage;

    public TaskService(StorageService storage) {
        this.storage = storage;
    }

    public Task createTask(int userId, String title, String description) {
        int id = idGen.generateId();
        Task task = new Task(id, userId, title, description);

        storage.getTasks().add(task);
        storage.saveTasks();
        return task;
    }

    public Task markTaskDone(int id) {
        for (Task task : storage.getTasks()) {
            if (task.getId() == id) {
                task.markDone();
                storage.saveTasks();
                return task;
            }
        }
        return null;
    }

    public List<Task> getTaskByUser(int userId) {
        List<Task> userTasks = new ArrayList<>();
        for (Task task : storage.getTasks()) {
            if (task.getUserId() == userId) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }
}
