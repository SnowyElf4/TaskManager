package src.service;

import java.util.List;

import src.repository.*;
import src.user.*;

public class UserService {
    private IdGenerator idGen = new IdGenerator();
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name) {
        List<User> users = userRepository.getUsers();
        User existUser = userRepository.findUserByName(name);

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

        userRepository.addUser(user);
        return user;
    }
}