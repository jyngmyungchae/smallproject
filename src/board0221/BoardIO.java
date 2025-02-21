package board0221;

import java.util.List;
import java.util.Scanner;

public class BoardIO {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Clear");
        System.out.println("4. Exit");
        System.out.print("Select menu: ");
    }

    public int getMenuChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayBoards(List<Board> boards) {
        if (boards.isEmpty()) {
            System.out.println("doesn`t exist");
        } else {
            boards.forEach(System.out::println);
        }
    }
}
