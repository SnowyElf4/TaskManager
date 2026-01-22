package src.ui.views;

import java.util.List;

import src.models.task.Task;

public class TaskView {
    public void showTasksMessage(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Task name: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Is done: " + task.isDone());
                System.out.println("Created at: " + task.getCreatedAt());
            }
        }
    }

    public void showTaskCreatedMessage(Task task) { System.out.println("Task created."); }
    public void showTaskDoneMessage() { System.out.println("Task marked as done."); }
    public void showTaskAlreadyDoneMessage() { System.out.println("Task already done."); }
    public void showTaskNotFoundMessage() { System.out.println("Task not found."); }
    public void showTaskNotBelongsToUserMessage() { System.out.println("Task not belongs to user."); }
}
