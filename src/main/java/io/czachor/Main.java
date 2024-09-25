package io.czachor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instantiate the database handler
        DatabaseHelper db = new DatabaseHelper();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nToDo List Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask(db, scanner);
                    break;
                case 2:
                    db.viewTasks();
                    break;
                case 3:
                    markAsCompleted(db, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    // Add a task from user input
    private static void addTask(DatabaseHelper db, Scanner scanner) {
        System.out.print("Enter the tasks description: ");
        String description = scanner.nextLine();
        db.addTask(description);
    }

    // Mark a task as completed from user input
    private static void markAsCompleted(DatabaseHelper db, Scanner scanner) {
        System.out.print("Enter the task ID to mark as completed: ");
        int taskId = scanner.nextInt();
        db.markAsCompleted(taskId);
    }
}
