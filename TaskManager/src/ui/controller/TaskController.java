package src.ui.controller;

import java.util.List;

import src.repository.*;
import src.service.TaskService;
import src.task.Task;
import src.ui.InputReader;
import src.ui.view.*;
import src.user.User;

public class TaskController {
    private UserRepository userRepository = new UserRepository();
    private TaskRepository taskRepository = new TaskRepository();
    private TaskService taskService = new TaskService(taskRepository, userRepository);
    private InputReader inputReader = new InputReader();
    private MenuView menuView = new MenuView();
    private TaskView taskView = new TaskView();

    public void showTasksFlow(User currentUser) {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        taskView.showTasks(userTasks);
    }

    public void createTaskFlow(User currentUser) {
        String inputTaskName = inputReader.readString("Write task name: ");
        String inputTaskDescription = inputReader.readString("Write description: ");

        Task task = taskService.createTask(currentUser.getId(), inputTaskName, inputTaskDescription);

        taskView.showTaskCreated(task);
    }

    public void markTaskDoneFlow(User currentUser) {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        taskView.showTasks(userTasks);

        if (userTasks.isEmpty()) {
            menuView.showMessage("No tasks to mark done.");
            return;
        }

        int input = inputReader.readInt("Enter task ID to mark done (0 for cancel): ", 0, 99999999);
        Task task = taskRepository.findTaskById(input);

        if (task == null || task.getUserId() != currentUser.getId()) {
            menuView.showMessage("Task not found or belongs to another user.");
            return;
        } else if (task.isDone()) {
            menuView.showMessage("Task already done.");
            return;
        } else {
            taskService.markTaskDone(input, currentUser.getId());
            taskView.showTaskDone();
        }
    }
}
