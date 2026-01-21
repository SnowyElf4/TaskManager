package src.repository;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import src.appconfig.AppConfig;
import src.task.Task;

public class TaskRepository {
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private AppConfig appConfig = new AppConfig();
    private List<Task> tasks = new ArrayList<>();

    public void loadTasks() {
        File file = new File(appConfig.getTasksFilePath());
        if (!file.exists()) {
            try {
                File newTaskJson = new File(appConfig.getTasksFilePath());
                if (newTaskJson.createNewFile()) {
                    try (Writer writer = new FileWriter(newTaskJson)) {
                        gson.toJson(tasks, writer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tasks = new ArrayList<>();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {
            }.getType());
            if (tasks == null)
                tasks = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            tasks = new ArrayList<>();
        }
    }

    public void saveTasks() {
        try (Writer writer = new FileWriter(appConfig.getTasksFilePath())) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        try {
            saveTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == updatedTask.getId()) {
                tasks.set(i, updatedTask);
                try {
                    saveTasks();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}