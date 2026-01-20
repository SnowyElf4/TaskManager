package src.service;

import java.util.*;

import src.user.*;
import src.repository.*;
import src.task.*;

public class TaskService {
    private IdGenerator idGen = new IdGenerator();
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(int userId, String title, String description) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            return null;
        }

        List<Task> tasks = taskRepository.getTasks();
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

        taskRepository.addTask(task);
        return task;
    }

    public Task markTaskDone(int taskId, int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            return null;

        Task task = taskRepository.findTaskById(taskId);
        if (task != null && task.getUserId() == userId && !task.isDone()) {
            task.markDone();
            taskRepository.updateTask(task);
            return task;
        }
        return null;
    }

    public List<Task> getCompletedTasksByUser(int userId) {
        List<Task> completedTasks = new ArrayList<>();
        List<Task> tasks = taskRepository.getTasks();

        for (Task task : tasks) {
            if (task.getUserId() == userId && task.isDone()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public List<Task> getPendingTasksByUser(int userId) {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = taskRepository.getTasks();

        for (Task task : tasks) {
            if (task.getUserId() == userId && !task.isDone()) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    public List<Task> getTaskByUser(int userId) {
        List<Task> userTasks = new ArrayList<>();
        for (Task task : taskRepository.getTasks()) {
            if (task.getUserId() == userId) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }
}