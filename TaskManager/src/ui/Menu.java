package src.ui;

import java.util.List;
import java.util.Scanner;

import src.service.StorageService;
import src.service.TaskService;
import src.service.UserService;
import src.task.Task;
import src.user.User;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private StorageService storage = new StorageService();
    private UserService userService = new UserService(storage);
    private TaskService taskService = new TaskService(storage);
    private User currentUser = null;

    public void run() {
        storage.loadUsers();
        storage.loadTasks();

        if (storage.getUsers().isEmpty()) {
            start(scanner);
        } else {
            chooseUser(scanner);
        }

        if (currentUser == null)
            return;
        menu(scanner);
    }

    public void chooseUser(Scanner scanner) {
        List<User> users = storage.getUsers();

        while (true) {
            System.out.println("Choose user:");

            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i).getName());
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= users.size()) {
                currentUser = users.get(choice - 1);
                return;
            } else {
                System.out.println("Incorrect choice.");
            }
        }
    }

    public void start(Scanner scanner) {
        while (true) {
            System.out.println(
                    """

                            1. Create user.

                            0. Exit.
                            """);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Write name.");
                    String name = scanner.nextLine();
                    currentUser = userService.createUser(name);
                    return;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect choice.");
                    break;
            }
        }
    }

    public void menu(Scanner scanner) {
        while (true) {
            System.out.println(
                    """
                            1. Create task.
                            2. Make task done.
                            3. Show all tasks.
                            4. Change user.

                            0. Exit.
                                """);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Write task name: ");
                    String title = scanner.nextLine();
                    System.out.print("Write description: ");
                    String description = scanner.nextLine();
                    Task task = taskService.createTask(currentUser.getId(), title, description);
                    System.out.println("Task created.\nID: " + task.getId() + "\nTask name: " + task.getTitle()
                            + "\nDescription: " + task.getDescription());
                    break;
                case 2:
                    List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
                    printTasks(userTasks);
                    System.out.println("Write ID of task for make done: ");
                    int inputTaskId = scanner.nextInt();
                    scanner.nextLine();
                    Task doneTask = taskService.markTaskDone(inputTaskId);
                    if (doneTask != null && doneTask.getUserId() == currentUser.getId()) {
                        System.out.println("Task done: " + doneTask.getTitle());
                    } else {
                        System.out.println("Task with this ID not found or of another user.");
                    }
                    break;
                case 3:
                    printTasks(taskService.getTaskByUser(currentUser.getId()));
                    break;
                case 4:
                    chooseUser(scanner);
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Incorrect choice.");
                    break;
            }
        }
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list empty.");
        } else {
            for (Task t : tasks) {
                System.out.println("ID: " + t.getId() + ", Task name: " + t.getTitle() + ", Is done: " + t.isDone());
            }
        }
    }
}
