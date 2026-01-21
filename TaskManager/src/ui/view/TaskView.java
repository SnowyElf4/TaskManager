package src.ui.view;

import java.util.List;

import src.task.Task;

public class TaskView {
    public void showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Task name: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Is done: " + task.isDone());
            }
        }
    }

    public void showTaskCreated(Task task) { System.out.println("Task created."); }
    public void showTaskDone() { System.out.println("Task marked as done."); }
}
