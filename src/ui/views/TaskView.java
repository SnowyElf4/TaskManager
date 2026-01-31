package ui.views;

import java.util.List;

import domain.task.Task;

public class TaskView {
    public void showTaskMenuMessage() {
        System.out.println("1. Show all task.");
        System.out.println("2. Create task.");
        System.out.println("3. Mark task done.");
        System.out.println("4. Edit task.");
        System.out.println("5. Delete task.");
        System.out.println("6. Filter tasks by status");
        System.out.println("7. Sort tasks by date.");
        System.out.println("0. Back to main menu");
    }

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

    public void showEditTaskMessage() {
        System.out.println("1. Edit title.");
        System.out.println("2. Edit description.");
        System.out.println("3. Edit all.");
        System.out.println("0. Back to task menu.");
    }

    public void showTaskTitleEditedMessage() { System.out.println("Title edited."); }
    public void showTaskDecriptionEditedMessage() { System.out.println("Description edited."); }
    public void showTaskCreatedMessage(Task task) { System.out.println("Task created."); }
    public void showTaskDoneMessage() { System.out.println("Task marked as done."); }
    public void showTaskAlreadyDoneMessage() { System.out.println("Task already done."); }
    public void showTaskNotFoundMessage() { System.out.println("Task not found."); }
    public void showTaskNotBelongsToUserMessage() { System.out.println("Task not belongs to user."); }
}
