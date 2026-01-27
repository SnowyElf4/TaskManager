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

    public void showTaskMenuFlow(User currentUser) {
        taskView.showTaskMenuMessage();

        int input = inputReader.readInt("Choose variant: ", 0, 7);

        switch (input) {
            case 1: showTasksFlow(currentUser); break;
            case 2: createTaskFlow(currentUser); break;
            case 3: markTaskDoneFlow(currentUser); break;
            case 4: 
            default:
                break;
        }
    }

    public void createTaskFlow(User currentUser) {
        String inputTaskName = inputReader.readString("Write task name: ");
        String inputTaskDescription = inputReader.readString("Write description: ");

        Task task = taskService.createTask(currentUser.getId(), inputTaskName, inputTaskDescription);

        taskView.showTaskCreatedMessage(task);
    }

    public void editTaskFlow(User currentUser) {
        List<Task> userTasks = taskService.getTaskByUser(currentUser.getId());
        taskView.showTasksMessage(userTasks);

        if (userTasks.isEmpty()) {
            menuView.showMessage("No tasks to edit.");
            return;
        }

        int taskId = inputReader.readInt("Enter task ID to edit (0 for cancel): ", 0, 99999999);
        if (taskId == 0) return;

        Task task = taskRepository.findTaskById(taskId);
        if (task == null) {
            taskView.showTaskNotFoundMessage();
            return;
        }
        if (task.getUserId() != currentUser.getId()) {
            taskView.showTaskNotBelongsToUserMessage();
            return;
        }

        boolean editing = true;
        while (editing) {
            taskView.showEditTaskMessage();
            int choice = inputReader.readInt("Choose variant: ", 0, 3);

            switch (choice) {
                case 0:
                    editing = false; 
                    break;
                case 1: 
                    String newTitle = inputReader.readString("Write new title: ");
                    task.setTitle(newTitle);
                    taskRepository.updateTask(task);
                    taskView.showTaskTitleEditedMessage();
                    break;
                case 2: 
                    String newDescription = inputReader.readString("Write new description: ");
                    task.setDescription(newDescription);
                    taskRepository.updateTask(task);
                    taskView.showTaskDecriptionEditedMessage();
                    break;
                case 3: 
                    newTitle = inputReader.readString("Write new title: ");
                    task.setTitle(newTitle);
                    newDescription = inputReader.readString("Write new description: ");
                    task.setDescription(newDescription);
                    taskRepository.updateTask(task);
                    taskView.showTaskTitleEditedMessage();
                    taskView.showTaskDecriptionEditedMessage();
                    break;
                default:
                    menuView.showMessage("Invalid option.");
            }
        }
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
