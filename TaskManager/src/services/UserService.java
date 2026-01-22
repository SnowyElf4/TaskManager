package src.services;

import src.models.user.*;
import src.repositories.*;

public class UserService {
    private UserRepository userRepository;
    private EntityService<User> genericService = new EntityService<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.genericService = new EntityService<>();
    }

    public User createUser(String name) {
        User existUser = userRepository.findUserByName(name);

        if (existUser != null) 
            return null;

        User user = new User();

        genericService.create(user);
        userRepository.addUser(user);

        return user;
    }
}