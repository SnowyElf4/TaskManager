package ui.views;

public class MenuView {
    public void showMainMenuMessage() { System.out.println("1. Task menu.\n2. Change user.\n0. Exit"); }
    public void showExitMessage() { System.out.println("You're exit."); }
    public void showMessage(String message) { System.out.println(message); }
}