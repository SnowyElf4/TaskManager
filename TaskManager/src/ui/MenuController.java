package src.ui;

import java.util.List;
import src.user.*;
import src.service.*;
import src.task.*;

public class MenuController {
    private User currentUser = null;
    private StorageService storageService = new StorageService();
    private UserService userService = new UserService(storageService);
    private TaskService taskService = new TaskService(storageService);
    private MenuView menuView = new MenuView();
    private InputReader inputReader = new InputReader();
    private boolean isRunning = true;

    public void run() {
        storageService.loadTasks();
        storageService.loadUsers();

        while (isRunning) {
            if (storageService.getUsers().isEmpty()) {
                registrationFlow();
            } else if (currentUser == null) {
                chooseUserFlow();
            } else {
                mainMenuFlow();
            }
        }
    }

    public void registrationFlow() {
        menuView.showRegistrationMenu();

        int input = inputReader.readInt(null, 0, 1);

        if (input == 0) {
            isRunning = false;
            return;
        }

        if (input == 1) {
            while (true) {
                String name = inputReader.readString("Write name: ");
                currentUser = userService.createUser(name);

                if (currentUser != null) {
                    break;
                } else {
                    menuView.showMessage("User already exists, try another name.");
                    continue;
                }
            }
        }
    }

    public void chooseUserFlow() {
        menuView.showUsers(storageService.getUsers());
        menuView.showMessage("0. Exit");

        int input = inputReader.readInt("Choose user: ", 0, storageService.getUsers().size());

        if (input == 0) {
            isRunning = false;
            return;
        }

        currentUser = storageService.getUsers().get(input - 1);
    }

    public void mainMenuFlow() {
        menuView.showMainMenu();

        int input = inputReader.readInt(null, 0, 4);

        switch (input) {
            case 0:
                isRunning = false;
                break;
            case 1:
                createTaskFlow();
                break;
            case 2:
                showTasksFlow();
                break;
            case 3:
                markTaskDoneFlow();
                break;
            case 4:
                currentUser = null;
                break;
            default:
                break;
        }
    }

    public void showTasksFlow() {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        menuView.showTasks(userTasks);
    }

    public void createTaskFlow() {
        String inputTaskName = inputReader.readString("Write task name: ");
        String inputTaskDescription = inputReader.readString("Write description: ");

        Task task = taskService.createTask(currentUser.getId(), inputTaskName, inputTaskDescription);

        menuView.showTaskCreated(task);
    }

    public void markTaskDoneFlow() {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        menuView.showTasks(userTasks);

        if (userTasks.isEmpty()) {
            menuView.showMessage("No tasks to mark done.");
            return;
        }

        int input = inputReader.readInt("Enter task ID to mark done (0 for cancel)", 0, 99999999);

        if (input == 0) {
            return;
        } else {
            Task doneTask = taskService.markTaskDone(input, currentUser.getId());
            if (doneTask != null) {
                menuView.showTaskDone();
            } else {
                menuView.showMessage("Task with this ID not found or belongs to another user.");
            }
        }
    }
}
