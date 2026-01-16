package src.ui;

import src.user.*;
import src.task.*;
import java.util.List;

public class MenuView {

    public void showRegistrationMenu() {
        System.out.println("1. Create user\n\n0.Exit");
    }

    public void showMainMenu() {
        System.out.println("1. Create task.\n2. Make task done.\n3. Show all tasks.\n4. Change user.\n0. Exit");
    }

    public void showUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }
    }

    public void showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Task name: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Is done:" + task.isDone());
            }
        }
    }

    public void showTaskCreated(Task task) {
        System.out.println("Task created.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
