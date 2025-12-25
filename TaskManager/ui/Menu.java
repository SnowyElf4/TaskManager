package TaskManager.ui;
import java.util.List;
import java.util.Scanner;
import TaskManager.service.TaskService;
import TaskManager.service.UserService;
import TaskManager.task.Task;
import TaskManager.user.User;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private TaskService taskService = new TaskService();
    private User currentUser = null;

    public void run() {
        while (true) {
            if (currentUser == null) {
                start(scanner);
            } else {
                menu(scanner);
            }
        }
    }

    public void start(Scanner scanner) {
        while (true) {
            System.out.println(
                """
                
                1. Создать пользователя.
                
                0. Выйти.
                """);
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите ваше имя.");
                    String name = scanner.nextLine();
                    currentUser = userService.createUser(name);
                    
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Такого пункта нет в меню.");
                    break;
            }
        }
    }

    public void menu(Scanner scanner) {
        while (true) {
            System.out.println(
                """
            1. Создать задачу.
            2. Выполнить задачу. (Сначала посмотрите ID задачи в списке всех задач).
            3. Показать все задачи.

            0. Выйти.
                """);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите название задачи: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите описание: ");
                    String description = scanner.nextLine();
                    Task task = taskService.createTask(currentUser.getId(), title, description);
                    System.out.println("Задача создана.\nID: " + task.getId() + "\nНазвание: " + task.getTitle() + "\nОписание: " + task.getDescription());
                    break;

                case 2:
                    List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
                    printTasks(userTasks);
                    System.out.println("Введите ID задачи для выполнения: ");
                    int inputTaskId = scanner.nextInt();
                    scanner.nextLine();
                    Task doneTask = taskService.markTaskDone(inputTaskId);
                    if (doneTask != null && doneTask.getUserId() == currentUser.getId()) {
                        System.out.println("Задача выполнена: " + doneTask.getTitle());
                    } else {
                        System.out.println("Задача с таким ID не найдена или принадлежит другому пользователю.");
                    }
                    break;

                case 3:
                    printTasks(taskService.getTaskByUser(currentUser.getId()));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Такого пункта нет в меню.");
                    break;
            }
        }
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            for (Task t : tasks) {
                System.out.println("ID: " + t.getId() + ", Название: " + t.getTitle() + ", Выполнена: " + t.isDone());
            }
        }
    }
}
    
