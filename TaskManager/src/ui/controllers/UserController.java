package src.ui.controllers;

import src.models.user.*;
import src.repositories.UserRepository;
import src.services.UserService;
import src.ui.*;
import src.ui.views.*;

public class UserController {
    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userRepository);
    InputReader inputReader = new InputReader();
    UserView userView = new UserView();
    MenuView menuView = new MenuView();

    public User registrationFlow() {
        User currentUser = null;
        userView.showUserRegistration();

        int input = inputReader.readInt("Choose variant: ", 0, 1);

        if (input == 0) { return null; }

        if (input == 1) {
            while (true) {
                String name = inputReader.readString("Write name: ");
                currentUser = userService.createUser(name);

                if (currentUser != null) { return currentUser; } 
                else {
                    menuView.showMessage("User already exists, try another name.");
                    continue;
                }
            }
        }
        return currentUser;
    }

    public User chooseUserFlow() {
        userView.showUsers(userRepository.getUsers());
        menuView.showMessage("0. Exit");

        int input = inputReader.readInt("Choose variant: ", 0, userRepository.getUsers().size());

        if (input == 0) { return null; }
        User currentUser = userRepository.getUsers().get(input - 1);
        return currentUser;
    }
}