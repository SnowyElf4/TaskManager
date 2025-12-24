package TaskManager.ui;
import java.util.List;
import java.util.Scanner;
import TaskManager.service.TaskService;
import TaskManager.service.UserService;
import TaskManager.task.Task;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private TaskService taskService = new TaskService();

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
                    userService.createUser(name);
                    menu(scanner);
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
            2. Выполнить задачу. (Прежде чем отметить - посмотрите ID задачи в списке всех задач).
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
                    Task task = taskService.createTask(title, description);
                    int taskId = task.getId(); 
                    System.out.println("Задача создана.\nID задачи: " + taskId + "\nНазвание задачи: " + task.getTitle() + "\nОписание задачи: " + task.getDescription());
                    break;
                case 2:
                    printTasks(taskService.getAllTask());
                    System.out.println("Введите ID задачи для выполнения: ");
                    int inputTaskId = scanner.nextInt();
                    scanner.nextLine();
                    Task doneTask = taskService.markTaskDone(inputTaskId);
                    if (doneTask != null) {
                        System.out.println("Задача выполнена: " + doneTask.getTitle());
                    } else {
                        System.out.println("Задача с таким ID не найдена.");
                    }
                    break;
                case 3:
                    printTasks(taskService.getAllTask());
                    break;
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
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId() + ", Название: " + task.getTitle() + ", Выполнена: " + task.isDone());
            }
        }
    }
}
