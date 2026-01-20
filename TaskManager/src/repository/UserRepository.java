package src.repository;

import java.util.*;
import src.user.*;
import src.appconfig.*;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;;

public class UserRepository {
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private AppConfig appConfig = new AppConfig();
    private List<User> users = new ArrayList<>();

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

    public void saveUsers() {
        try (Writer writer = new FileWriter(appConfig.getUsersFilePath())) {
            gson.toJson(users, writer);
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

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User findUserByName(String name) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}