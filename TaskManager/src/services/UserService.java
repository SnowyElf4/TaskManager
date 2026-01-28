package src.services;

import java.util.List;
import src.models.user.*;
import src.repositories.*;

public class UserService {
    private UserRepository userRepository;
    private IdGenerator idGen = new IdGenerator();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name) {
        User existUser = userRepository.findUserByName(name);

        // null validation here because Optional redundant
        if (existUser != null)
            return null;

        User user = new User();

        int id = generateUserId();
        
        user.setId(id);
        user.setName(name);
        userRepository.addUser(user);

        return user;
    }

    private int generateUserId() {
        int id;
        do {
            id = idGen.generateId();
        } while (userRepository.findUserById(id) != null);
        return id;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }
}