package ui.controllers;

import domain.user.*;
import services.UserService;
import ui.*;
import ui.views.*;

public class UserController {
    InputReader inputReader = new InputReader();
    UserView userView = new UserView();
    MenuView menuView = new MenuView();
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User resolveUserFlow() {
        if (userService.getUsers().isEmpty()) {
            return registrationFlow();
        } else {
            return chooseUserFlow();
        }
    }

    public User registrationFlow() {
        User currentUser = null;
        userView.showUserRegistration();

        int input = inputReader.readInt("Choose variant: ", 0, 1);

        if (input == 0) {
            return null;
        }

        if (input == 1) {
            while (true) {
                String name = inputReader.readString("Write name: ");
                currentUser = userService.createUser(name);

                if (currentUser != null) {
                    return currentUser;
                } else {
                    menuView.showMessage("User already exists, try another name.");
                    continue;
                }
            }
        }
        return currentUser;
    }

    public User chooseUserFlow() {
        userView.showUsers(userService.getUsers());
        menuView.showMessage("0. Exit");

        int input = inputReader.readInt("Choose variant: ", 0, userService.getUsers().size());

        if (input == 0) {
            return null;
        }
        User currentUser = userService.getUsers().get(input - 1);
        return currentUser;
    }
}