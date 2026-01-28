package src.ui;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            int value = 0;

            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }

            if (value < min || value > max) {
                System.out.println("Please enter a number between " + min + " and " + max);
                continue;
            }
            return value;
        }
    }

    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("The string cannot be empty.");
                continue;
            }
            return input;
        }
    }
}