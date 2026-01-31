package services;

import java.util.*;

import domain.task.*;
import repositories.*;

public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private IdGenerator idGen = new IdGenerator();

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Optional<Task> createTask(int userId, String title, String description) {
        return Optional.ofNullable(userRepository.findUserById(userId))
                .map(user -> {
                    Task task = new Task(userId, title, description);

                    int id = generateTaskId();
                    task.setId(id);

                    taskRepository.addTask(task);
                    return task;
                });
    }

    public Optional<Task> markTaskDone(int taskId, int userId) {
        return Optional.ofNullable(userRepository.findUserById(userId))
                .flatMap(user -> taskRepository.findTaskByIdForUser(taskId, user.getId()))
                .filter(task -> !task.isDone())
                .map(task -> {
                    task.markDone();
                    taskRepository.updateTask(task);
                    return task;
                });
    }

    public List<Task> getCompletedTasksByUser(int userId) {
        return taskRepository.getCompletedTasksByUser(userId);
    }

    public List<Task> getPendingTasksByUser(int userId) {
        return taskRepository.getPendingTasksByUser(userId);
    }

    public List<Task> getTasksByDate(int userId) {
        List<Task> tasks = taskRepository.getTaskByUser(userId);
        tasks.sort(Comparator.comparing(Task::getCreatedAt));
        return tasks;
    }

    private int generateTaskId() {
        int id;
        do {
            id = idGen.generateId();
        } while (taskRepository.findTaskById(id) != null);
        return id;
    }
}