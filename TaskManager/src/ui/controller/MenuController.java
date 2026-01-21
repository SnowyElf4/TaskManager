package src.ui.controller;

import src.user.*;
import src.repository.*;
import src.ui.InputReader;
import src.ui.view.*;

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
            } else if (currentUser == null) {
                currentUser = userController.chooseUserFlow();
            } else {
                mainMenuFlow();
            }
        }
    }

    public void mainMenuFlow() {
        menuView.showMainMenu();

        int input = inputReader.readInt("Choose variant: ", 0, 4);

        switch (input) {
            case 0:
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