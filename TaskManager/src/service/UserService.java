package src.service;

import java.util.List;

import src.user.*;

public class UserService {
    private IdGenerator idGen = new IdGenerator();
    private StorageService storage;

    public UserService(StorageService storage) {
        this.storage = storage;
    }

    public User createUser(String name) {
        List<User> users = storage.getUsers();
        User existUser = findUserByName(name);

        if (existUser != null)
            return null;

        int id = idGen.generateId();

        while (true) {
            boolean uniq = true;
            for (User existingUser : users) {
                if (existingUser.getId() == id) {
                    id = idGen.generateId();
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                break;
            }
        }

        User user = new User();
        user.setId(id);
        user.setName(name);

        storage.addUser(user);
        storage.saveUsers();
        return user;
    }

    public User findUserByName(String name) {
        List<User> users = storage.getUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
