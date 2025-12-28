package src.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import src.task.Task;
import src.user.User;
import src.appconfig.AppConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageService {
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private AppConfig appConfig = new AppConfig();
    private List<User> users = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public void loadUsers() {
        File file = new File(appConfig.getUsersFilePath());
        if (!file.exists()) {
            try {
                File newUserJson = new File(appConfig.getUsersFilePath());
                if (newUserJson.createNewFile()) {
                    try (Writer writer = new FileWriter(newUserJson)) {
                        gson.toJson(users, writer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            users = new ArrayList<>();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
            if (users == null)
                users = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

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

    public void saveUsers() {
        try (Writer writer = new FileWriter(appConfig.getUsersFilePath())) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTasks() {
        try (Writer writer = new FileWriter(appConfig.getTasksFilePath())) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        users.add(user);
        try {
            saveUsers();
        } catch (Exception e) {
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

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
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

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
