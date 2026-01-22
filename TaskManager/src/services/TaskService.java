package src.services;

import java.util.*;

import src.models.task.*;
import src.models.user.*;
import src.repositories.*;

public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private EntityService<Task> genericService = new EntityService<>();

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.genericService = new EntityService<>();
    }

    public Task createTask(int userId, String title, String description) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            return null;
        }

        Task task = new Task(userId, title, description);

        genericService.create(task);
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