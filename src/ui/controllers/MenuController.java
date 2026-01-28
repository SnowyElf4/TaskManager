package src.ui.controllers;

import src.models.user.*;
import src.ui.InputReader;
import src.ui.views.*;

public class MenuController {
    private User currentUser = null;
    private MenuView menuView = new MenuView();
    private InputReader inputReader = new InputReader();
    private TaskController taskController;
    private UserController userController;
    private boolean isRunning = true;

    public MenuController(UserController userController, TaskController taskController) {
        this.userController = userController;
        this.taskController = taskController;
    }

    public void run() {
        while (isRunning) {
            currentUser = userController.resolveUserFlow();
            if (currentUser == null) {
                menuView.showExitMessage();
                isRunning = false;
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