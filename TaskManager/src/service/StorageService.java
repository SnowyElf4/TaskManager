package src.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import src.task.Task;
import src.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageService {
    private Gson gson = new Gson();

    private final String USERS_FILE = "TaskManager/data/users.json";
    private final String TASKS_FILE = "TaskManager/data/tasks.json";

    private List<User> users = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public StorageService() {
        File dataDir = new File("data");
        if (!dataDir.exists())
            dataDir.mkdir();
    }

    public void loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
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
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
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
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTasks() {
        try (Writer writer = new FileWriter(TASKS_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
