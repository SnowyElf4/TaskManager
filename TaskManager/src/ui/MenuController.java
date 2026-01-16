package src.ui;

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

    public void chooseUser() {
        if (currentUser == null) {
            menuView.showRegistrationMenu();
        } else {
            menuView.showMainMenu();
        }

        int input = inputReader.readInt(null, 0, 1);

        if (input == 0) {
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

    public void run() {
        storageService.loadTasks();
        storageService.loadUsers();

        if (storageService.getUsers().isEmpty()) {
            menuView.showRegistrationMenu();
        } else {
            menuView.showUsers(storageService.getUsers());

            int userIndex = inputReader.readInt("Choose user: ", 1, storageService.getUsers().size());
            currentUser = storageService.getUsers().get(userIndex);
        }
    }
}
