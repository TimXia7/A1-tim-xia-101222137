package main;

import LibraryStates.LibraryState;
import LibraryStates.LoginState;

import java.util.Scanner;

public class LibraryUI {
    private final Library library;
    private LibraryState state;
    private final Scanner scanner = new Scanner(System.in);
    private Boolean running;

    public LibraryUI(Library library) {
        this.library = library;
        this.state = new LoginState();
        this.running = true;
    }

    // Finite State Machine:
    public void run() {
        while (running) {
            state.run(library, this);
        }
    }

    public void setState(LibraryState state) { this.state = state; }
    public void stop(){ this.running = false; }


    // === UI helper methods ===
    public String[] promptCredentials() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        clearScreen();
        return new String[]{username, password};
    }

    public int mainOptions() {
        System.out.print("Please enter an operation (1,2,0) \n");
        System.out.print("1: Borrow a Book \n");
        System.out.print("2: Return a book \n");
        System.out.print("3: Logout \n");
        String choice = scanner.nextLine();

        clearScreen();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    private void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


}
