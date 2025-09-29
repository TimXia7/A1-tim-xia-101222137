import java.util.Scanner;

public class LibraryUI {
    Library library;

    public LibraryUI(Library library){
        this.library = library;
    }

    public String[] promptCredentials() {
        Scanner scanner = new Scanner(System.in);

        // Clear the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return new String[] { username, password };
    }
}
