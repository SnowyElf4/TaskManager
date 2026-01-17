package src.service;

import java.util.ArrayList;
import java.util.List;

import src.user.User;
import src.task.*;

public class TaskService {
    private IdGenerator idGen = new IdGenerator();
    private StorageService storage;

    public TaskService(StorageService storage) {
        this.storage = storage;
    }

    public Task createTask(int userId, String title, String description) {
        User user = storage.findUserById(userId);

        if (user == null) {
            return null;
        }

        List<Task> tasks = storage.getTasks();
        int id = idGen.generateId();

        while (true) {
            boolean uniq = true;
            for (Task existingTask : tasks) {
                if (existingTask.getId() == id) {
                    id = idGen.generateId();
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                break;
            }
        }

        Task task = new Task(id, userId, title, description);

        storage.addTask(task);
        storage.saveTasks();
        return task;
    }

    public Task markTaskDone(int taskId, int userId) {
        User user = storage.findUserById(userId);
        if (user == null)
            return null;

        Task task = storage.findTaskById(taskId);
        if (task != null && task.getUserId() == userId && !task.isDone()) {
            task.markDone();
            storage.saveTasks();
            return task;
        }
        return null;
    }

    public List<Task> getCompletedTasksByUser(int userId) {
        List<Task> completedTasks = new ArrayList<>();
        List<Task> tasks = storage.getTasks();

        for (Task task : tasks) {
            if (task.getUserId() == userId && task.isDone()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public List<Task> getPendingTasksByUser(int userId) {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = storage.getTasks();

        for (Task task : tasks) {
            if (task.getUserId() == userId && !task.isDone()) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
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
