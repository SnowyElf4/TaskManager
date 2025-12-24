package TaskManager;
import TaskManager.ui.Menu;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        menu.start(scanner);
        scanner.close();
    }
}
