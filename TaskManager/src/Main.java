package src;

import java.util.Scanner;

import src.service.StorageService;
import src.ui.Menu;

class Main {
    public static void main(String[] args) {
        StorageService storage = new StorageService();
        storage.loadTasks();
        storage.loadUsers();
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        menu.run();
        scanner.close();
    }
}
