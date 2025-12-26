package src.service;

import com.google.gson.Gson;
import src.task.Task;
import src.user.User;

import java.io.FileReader;
import java.io.Writer;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class StorageService {
    private Gson gson = new Gson();
    private final String USERS_FILE = "data/user.json";
    private final String TASKS_FILE = "data/task.json";
    private List<User> users = new ArrayList<User>();
    private List<Task> tasks = new ArrayList<Task>();

    public void loadUsers() {
        try (Reader reader = new FileReader(USERS_FILE)) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadTasks() {
        try (Reader reader = new FileReader(TASKS_FILE)) {
            tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {
            }.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveUsers() {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveTasks() {
        try (Writer writer = new FileWriter(TASKS_FILE)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
