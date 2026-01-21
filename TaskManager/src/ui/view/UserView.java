package src.ui.view;

import java.util.List;

import src.user.User;

public class UserView {
    public void showUserRegistration() { System.out.println("1. Create user\n\n0.Exit"); }

    public void showUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }
    }
}
