package src.repositories;

import java.util.*;
import java.util.function.Predicate;

import com.google.gson.reflect.TypeToken;

import src.appconfig.AppConfig;
import src.models.task.Task;

public class TaskRepository {
    private AppConfig appConfig = new AppConfig();
    private JsonRepository<Task> taskRepository = new JsonRepository<>(appConfig.getTasksFilePath(), new TypeToken<List<Task>>() {}.getType());

    public TaskRepository() { taskRepository.load(); }
    public void addTask(Task task) { taskRepository.add(task); }
    public Task findTaskById(int id) { return taskRepository.find(t -> t.getId() == id); }
    public void updateTask(Task updatedTask) { taskRepository.update(t -> t.getId() == updatedTask.getId(), updatedTask); }
    public List<Task> getTasks() { return taskRepository.getAll(); }

    private List<Task> filterTasks(Predicate<Task> predicate) {
        return getTasks().stream()
                     .filter(predicate)
                     .toList();
    }

    public Optional<Task> findTaskByIdForUser(int taskId, int userId) {
        return Optional.ofNullable(findTaskById(taskId))
                    .filter(task -> task.getUserId() == userId);
    }

    public List<Task> getTaskByUser(int userId) { return filterTasks(task -> task.getUserId() == userId); }
    public List<Task> getCompletedTasksByUser(int userId) { return filterTasks(task -> task.getUserId() == userId && task.isDone()); }
    public List<Task> getPendingTasksByUser(int userId) { return filterTasks(task -> task.getUserId() == userId && !task.isDone()); }
}