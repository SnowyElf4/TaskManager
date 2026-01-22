package src.ui.controllers;

import java.util.List;

import src.models.task.Task;
import src.models.user.User;
import src.repositories.*;
import src.services.TaskService;
import src.ui.InputReader;
import src.ui.views.*;

public class TaskController {
    private UserRepository userRepository = new UserRepository();
    private TaskRepository taskRepository = new TaskRepository();
    private TaskService taskService = new TaskService(taskRepository, userRepository);
    private InputReader inputReader = new InputReader();
    private MenuView menuView = new MenuView();
    private TaskView taskView = new TaskView();

    public void showTasksFlow(User currentUser) {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        taskView.showTasksMessage(userTasks);
    }

    public void createTaskFlow(User currentUser) {
        String inputTaskName = inputReader.readString("Write task name: ");
        String inputTaskDescription = inputReader.readString("Write description: ");

        Task task = taskService.createTask(currentUser.getId(), inputTaskName, inputTaskDescription);

        taskView.showTaskCreatedMessage(task);
    }

    public void markTaskDoneFlow(User currentUser) {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        taskView.showTasksMessage(userTasks);

        if (userTasks.isEmpty()) {
            menuView.showMessage("No tasks to mark done.");
            return;
        }

        int input = inputReader.readInt("Enter task ID to mark done (0 for cancel): ", 0, 99999999);
        Task task = taskRepository.findTaskById(input);

        
        if (task == null) {
            taskView.showTaskNotFoundMessage();
            return;
        } else if (task.getUserId() != currentUser.getId()) {
            taskView.showTaskNotBelongsToUserMessage();
            return;
        } else if (task.isDone()) {
            taskView.showTaskAlreadyDoneMessage();
            return;
        } else {
            taskService.markTaskDone(input, currentUser.getId());
            taskView.showTaskDoneMessage();
        }
    }
}
