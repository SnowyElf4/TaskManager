package src.ui.views;

public class MenuView {
    public void showMainMenuMessage() { System.out.println("1. Create task.\n2. Make task done.\n3. Show all tasks.\n4. Change user.\n0. Exit"); }
    public void showExitMessage() { System.out.println("You're exit."); }
    public void showMessage(String message) { System.out.println(message); }
}