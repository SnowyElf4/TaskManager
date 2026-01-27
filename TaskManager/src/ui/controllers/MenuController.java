package src.ui.controllers;

import src.models.user.*;
import src.repositories.*;
import src.ui.InputReader;
import src.ui.views.*;

public class MenuController {
    private User currentUser = null;
    private UserRepository userRepository = new UserRepository();
    private MenuView menuView = new MenuView();
    private InputReader inputReader = new InputReader();
    private UserController userController = new UserController();
    private TaskController taskController = new TaskController();
    private boolean isRunning = true;

    public void run() {
        while (isRunning) {
            if (userRepository.getUsers().isEmpty()) {
                currentUser = userController.registrationFlow();
                if (currentUser == null) {
                    menuView.showExitMessage();
                    isRunning = false;
                }
            } else if (currentUser == null) {
                currentUser = userController.chooseUserFlow();
                if (currentUser == null) {
                    menuView.showExitMessage();
                    isRunning = false;
                }
            } else {
                mainMenuFlow();
            }
        }
    }

    public void mainMenuFlow() {
        menuView.showMainMenuMessage();

        int input = inputReader.readInt("Choose variant: ", 0, 4);

        switch (input) {
            case 0:
                menuView.showExitMessage();
                isRunning = false;
                break;
            case 1:
                taskController.createTaskFlow(currentUser);
                break;
            case 2:
                taskController.markTaskDoneFlow(currentUser);
                break;
            case 3:
                taskController.showTasksFlow(currentUser);
                break;
            case 4:
                currentUser = null;
                break;
            default:
                break;
        }
    }
}