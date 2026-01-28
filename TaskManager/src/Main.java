package src;

import src.repositories.*;
import src.services.*;
import src.ui.controllers.*;

public class Main {
    private UserRepository userRepository = new UserRepository();
    private TaskRepository taskRepository = new TaskRepository();
    private UserService userService = new UserService(userRepository);
    private TaskService taskService = new TaskService(taskRepository, userRepository);
    private UserController userController = new UserController(userService);
    private TaskController taskController = new TaskController(taskService);
    private MenuController menuController = new MenuController(userController, taskController);

    public static void main(String[] args) {
        new Main().menuController.run();
    }
}