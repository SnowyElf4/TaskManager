package src.repositories;

import java.util.*;

import com.google.gson.reflect.TypeToken;

import src.appconfig.*;
import src.models.user.*;

public class UserRepository {
    private AppConfig appConfig = new AppConfig();
    private JsonRepository<User> userRepository = new JsonRepository<>(appConfig.getUsersFilePath(), new TypeToken<List<User>>() {}.getType());

    public UserRepository() { userRepository.load(); }
    public void addUser(User user) { userRepository.add(user);}
    public User findUserById(int id) { return userRepository.find(u -> u.getId() == id); }
    public User findUserByName(String name) { return userRepository.find(u -> u.getName().equals(name)); }
    public List<User> getUsers() { return userRepository.getAll(); }
}