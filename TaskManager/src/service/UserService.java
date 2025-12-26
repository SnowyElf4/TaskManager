package src.service;

import src.user.*;

public class UserService {
    private IdGenerator idGen = new IdGenerator();
    private StorageService storage;

    public UserService(StorageService storage) {
        this.storage = storage;
    }

    public User createUser(String name) {
        int id = idGen.generateId();
        User user = new User();
        user.setId(id);
        user.setName(name);

        storage.getUsers().add(user);
        storage.saveUsers();
        return user;
    }
}
