package ui.controllers;

import services.*;
import ui.views.*;
import ui.*;
import domain.user.User;
import domain.task.Task;

import java.util.*;

public class TaskController {
    private TaskService taskService;
    private InputReader inputReader = new InputReader();
    private TaskView taskView = new TaskView();
    private MenuView menuView = new MenuView();

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public void createTaskFlow(User currentUser) {
        String title = inputReader.readString("Write task name: ");
        String description = inputReader.readString("Write description: ");

        Optional<Task> task = taskService.createTask(currentUser.getId(), title, description);
        if (task.isPresent()) {
            taskView.showTaskCreatedMessage(task.get());
        } else {
            menuView.showMessage("Task could not be created.");
        }
    }

    public void markTaskDoneFlow(User currentUser) {
        int taskId = inputReader.readInt("Enter task ID to mark done: ", 0, 99999999);

        Optional<Task> task = taskService.markTaskDone(taskId, currentUser.getId());
        if (task.isPresent()) {
            taskView.showTaskDoneMessage();
        } else {
            taskView.showTaskAlreadyDoneMessage();
        }
    }

    public void showTasksFlow(User currentUser) {
        List<Task> tasks = taskService.getTasksByDate(currentUser.getId());
        taskView.showTasksMessage(tasks);
    }
}
